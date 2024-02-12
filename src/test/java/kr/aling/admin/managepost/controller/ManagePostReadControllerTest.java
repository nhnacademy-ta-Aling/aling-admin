package kr.aling.admin.managepost.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.aling.admin.managepost.dto.response.ReadManagePostResponseDto;
import kr.aling.admin.managepost.exception.ManagePostNotFoundException;
import kr.aling.admin.managepost.service.ManagePostReadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs(outputDir = "target/snippets")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ManagePostReadController.class)
class ManagePostReadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagePostReadService managePostReadService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {}

    @Test
    @DisplayName("관리게시글 페이징 조회 성공")
    void getManagePosts() throws Exception {
        // given
        int page = 0;
        int size = 3;


        // when
        ResultActions result = mockMvc.perform(get("/api/v1/manage-posts")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print())
                .andExpect(status().isOk());

        verify(managePostReadService, times(1)).getManagePosts(null, PageRequest.of(page, size));

        // docs
        result.andDo(document("get-manage-posts",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(
                        parameterWithName("size").description("페이지네이션 사이즈"),
                        parameterWithName("page").description("페이지네이션 페이지 번호")
                ),
                responseFields(
                        fieldWithPath("content").type(JsonFieldType.BOOLEAN).description("동작 성공 여부")
                )));

        //{
        //    "content": [
        //        {
        //            "userNo": 1,
        //            "type": "NOTICE",
        //            "title": "공지사항 등록 테스트"
        //        },
        //    ],
        //    "pageable": {
        //        "sort": {
        //            "empty": true,
        //            "sorted": false,
        //            "unsorted": true
        //        },
        //        "offset": 0,
        //        "pageNumber": 0,
        //        "pageSize": 20,
        //        "paged": true,
        //        "unpaged": false
        //    },
        //    "last": true,
        //    "totalPages": 1,
        //    "totalElements": 4,
        //    "sort": {
        //        "empty": true,
        //        "sorted": false,
        //        "unsorted": true
        //    },
        //    "size": 20,
        //    "number": 0,
        //    "first": true,
        //    "numberOfElements": 4,
        //    "empty": false
        //}
    }

    @Test
    @DisplayName("관리게시글 번호 조회 성공")
    void getManagePost() throws Exception {
        // given
        long no = 1L;
        ReadManagePostResponseDto responseDto = new ReadManagePostResponseDto(1L, "NOTICE", "Test Title", "Test Content");
        when(managePostReadService.getManagePost(no)).thenReturn(responseDto);

        // when
        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/manage-posts/{no}", no)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userNo", equalTo(1)))
                .andExpect(jsonPath("$.type", equalTo(responseDto.getType())))
                .andExpect(jsonPath("$.title", equalTo(responseDto.getTitle())))
                .andExpect(jsonPath("$.content", equalTo(responseDto.getContent())));

        verify(managePostReadService, times(1)).getManagePost(no);

        // docs
        result.andDo(document("get-manage-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("no").description("관리게시물 번호")),
                responseFields(
                        fieldWithPath("userNo").type(JsonFieldType.NUMBER).description("관리게시물 등록한 회원의 번호"),
                        fieldWithPath("type").type(JsonFieldType.STRING).description("관리게시물 타입"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                )));
    }

    @Test
    @DisplayName("관리게시글 번호 조회 실패 - 없는 게시글인 경우")
    void getManagePost_notFoundManagePost() throws Exception {
        // given
        when(managePostReadService.getManagePost(anyLong())).thenThrow(ManagePostNotFoundException.class);

        // when
        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/manage-posts/{no}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print())
                .andExpect(status().isNotFound());

        verify(managePostReadService, times(1)).getManagePost(anyLong());
    }
}