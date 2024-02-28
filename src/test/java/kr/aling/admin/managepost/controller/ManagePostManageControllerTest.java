package kr.aling.admin.managepost.controller;

import static kr.aling.admin.util.RestDocsUtil.REQUIRED;
import static kr.aling.admin.util.RestDocsUtil.REQUIRED_YES;
import static kr.aling.admin.util.RestDocsUtil.VALID;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.aling.admin.managepost.dto.request.CreateManagePostRequestDto;
import kr.aling.admin.managepost.dto.request.ModifyManagePostRequestDto;
import kr.aling.admin.managepost.dto.response.CreateManagePostResponseDto;
import kr.aling.admin.managepost.dummy.ManagePostDummy;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.service.ManagePostManageService;
import kr.aling.admin.user.exception.UserNotFoundException;
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

@AutoConfigureRestDocs
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
        ResultActions perform = mockMvc.perform(post("/api/v1/manage-posts")
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
                        fieldWithPath("userNo").type(JsonFieldType.NUMBER).description("관리자 ID")
                                .attributes(key("valid").value("Not Null, 양수")),
                        fieldWithPath("type").type(JsonFieldType.STRING).description("관리게시글 타입")
                                .attributes(key("valid").value("NOTICE, FAQ, POLICY")),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목")
                                .attributes(key("valid").value("Not Blank, 최대 100자")),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                .attributes(key("valid").value("Not Blank"))
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
        ResultActions perform = mockMvc.perform(post("/api/v1/manage-posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        perform.andDo(print()).andExpect(status().isBadRequest());
        verify(managePostManageService, never()).registerManagePost(any(CreateManagePostRequestDto.class));
    }

    @Test
    @DisplayName("관리게시글 등록 실패 - 회원을 찾지 못한 경우")
    void registerManagePost_notFoundUser() throws Exception {
        // given
        CreateManagePostRequestDto requestDto = new CreateManagePostRequestDto(
                managePost.getManagePostNo(), managePost.getType(), managePost.getTitle(), managePost.getContent()
        );
        when(managePostManageService.registerManagePost(any()))
                .thenThrow(new UserNotFoundException(managePost.getManagePostNo()));

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/manage-posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        // then
        perform.andDo(print()).andExpect(status().isNotFound());
        verify(managePostManageService, times(1)).registerManagePost(any(CreateManagePostRequestDto.class));
    }

    @Test
    @DisplayName("관리게시글 수정 성공")
    void modifyManagePost() throws Exception {
        // given
        ModifyManagePostRequestDto requestDto = new ModifyManagePostRequestDto(
                managePost.getType(), managePost.getTitle(), managePost.getContent()
        );

        // when
        ResultActions perform =
                mockMvc.perform(put("/api/v1/manage-posts/{managePostNo}", managePost.getManagePostNo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)));

        // then
        perform.andDo(print())
                .andExpect(status().isOk());

        // docs
        perform.andDo(document("modify-manage-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                        parameterWithName("managePostNo").description("관리게시물 번호")
                ),
                requestFields(
                        fieldWithPath("type").type(JsonFieldType.STRING).description("관리게시글 타입")
                                .attributes(key(REQUIRED).value(REQUIRED_YES))
                                .attributes(key(VALID).value("NOTICE, FAQ, POLICY")),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목")
                                .attributes(key(REQUIRED).value(REQUIRED_YES))
                                .attributes(key(VALID).value("Not Blank, 최대 100자")),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                .attributes(key(REQUIRED).value(REQUIRED_YES))
                                .attributes(key(VALID).value("Not Blank"))
                )));
    }

    @Test
    @DisplayName("관리게시글 수정 실패 - 입력 데이터가 @Valid 검증 조건에 맞지 않은 경우")
    void modifyManagePost_invalidInput() throws Exception {
        // given
        ModifyManagePostRequestDto requestDto = new ModifyManagePostRequestDto(
                null, "", ""
        );

        // when
        ResultActions perform =
                mockMvc.perform(put("/api/v1/manage-posts/{managePostNo}", managePost.getManagePostNo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)));

        // then
        perform.andDo(print()).andExpect(status().isBadRequest());

        verify(managePostManageService, never()).modifyManagePost(anyLong(), any(ModifyManagePostRequestDto.class));
    }

    @Test
    @DisplayName("관리게시글 삭제 성공")
    void deleteManagePost() throws Exception {
        // given
        // when
        ResultActions perform =
                mockMvc.perform(delete("/api/v1/manage-posts/{managePostNo}", managePost.getManagePostNo()));

        // then
        perform.andDo(print())
                .andExpect(status().isNoContent());

        // docs
        perform.andDo(document("delete-manage-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                        parameterWithName("managePostNo").description("관리게시물 번호")
                )));
    }
}