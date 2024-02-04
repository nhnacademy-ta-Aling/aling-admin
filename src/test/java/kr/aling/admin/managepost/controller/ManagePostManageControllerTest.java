package kr.aling.admin.managepost.controller;


import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.aling.admin.managepost.dto.request.CreateManagePostRequestDto;
import kr.aling.admin.managepost.dto.response.CreateManagePostResponseDto;
import kr.aling.admin.managepost.dummy.ManagePostDummy;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.service.ManagePostManageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs(outputDir = "target/snippets")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ManagePostManageController.class)
class ManagePostManageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagePostManageService managePostManageService;

    @Autowired
    private ObjectMapper objectMapper;

    private ManagePost managePost;

    @BeforeEach
    void setUp() {
        managePost = ManagePostDummy.dummy();
    }

    @Test
    @DisplayName("관리게시글 등록 성공")
    void registerManagePost() throws Exception {
        // given
        int managePostNo = 1;
        CreateManagePostRequestDto requestDto = new CreateManagePostRequestDto(
                managePost.getManagePostNo(), managePost.getType(), managePost.getTitle(), managePost.getContent()
        );
        CreateManagePostResponseDto responseDto = new CreateManagePostResponseDto((long) managePostNo);
        when(managePostManageService.registerManagePost(any())).thenReturn(responseDto);

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/manageposts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.managePostNo", equalTo(managePostNo)));

        // docs
        perform.andDo(document("register-manage-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("userNo").type(JsonFieldType.NUMBER).description("관리자 ID"),
                        fieldWithPath("type").type(JsonFieldType.STRING).description("관리게시글 타입"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                ),
                responseFields(
                        fieldWithPath("managePostNo").type(JsonFieldType.NUMBER).description("관리게시글 번호")
                )));
    }

    @Test
    @DisplayName("관리게시글 등록 실패 - 입력 데이터가 @Valid 검증 조건에 맞지 않은 경우")
    void registerManagePost_invalidInput() throws Exception {
        // given
        CreateManagePostRequestDto requestDto = new CreateManagePostRequestDto(0L, "", "", "");

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/manageposts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        perform.andDo(print()).andExpect(status().isBadRequest());
        verify(managePostManageService, never()).registerManagePost(requestDto);
    }
}