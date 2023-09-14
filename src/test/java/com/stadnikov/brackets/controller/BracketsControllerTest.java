package com.stadnikov.brackets.controller;

import com.stadnikov.brackets.model.TextRequest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BracketsControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/api/checkBrackets";

    @Test
    void checkBrackets() throws Exception {
        String original = "Вчера я отправился в поход в лес (это мое любимое место для отдыха) вместе с друзьями. " +
                "Мы выбрали маршрут, который проходил через горные потоки и поля (для разнообразия). " +
                "В начале пути погода была отличной, солнце светило ярко, и птицы радостно пели. " +
                "Однако, когда мы подошли ближе к вершине горы, небо стало покрываться облаками, " +
                "(как будто природа готовила нам небольшой сюрприз). Несмотря на это, виды были захватывающими, " +
                "особенно когда мы достигли высшей точки и увидели прекрасный вид на долину (я почувствовал, что " +
                "все усилия стоили того).";
        TextRequest textRequest = new TextRequest(original);
        perform(postJson(REST_URL, textRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value("true"));
    }

    @Test
    void checkBracketsWithEmptyText() throws Exception {
        TextRequest textRequest = new TextRequest(" ");
        perform(postJson(REST_URL, textRequest))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkBracketsWithInvalidJson() throws Exception {
        perform(postJson(REST_URL, null))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkBracketsWithTextOnly() throws Exception {
        TextRequest textRequest = new TextRequest("abc def");
        perform(postJson(REST_URL, textRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value("true"));
    }

    @Test
    void checkBracketsWithEmptyBrackets() throws Exception {
        TextRequest textRequest = new TextRequest("abc ( ) def");
        perform(postJson(REST_URL, textRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value("false"));
    }

    @Test
    void checkBracketsWithMultipleBrackets() throws Exception {
        TextRequest textRequest = new TextRequest("([a] <d>)");
        perform(postJson(REST_URL, textRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value("true"));
    }

    @Test
    void checkBracketsWithWrongClosedBrackets() throws Exception {
        TextRequest textRequest = new TextRequest("((abc)]");
        perform(postJson(REST_URL, textRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value("false"));
    }

    @Test
    void checkBracketsWithNotClosedBrackets() throws Exception {
        TextRequest textRequest = new TextRequest("([<{abc");
        perform(postJson(REST_URL, textRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value("false"));
    }

    @Test
    void checkBracketsWithNotOpenedBrackets() throws Exception {
        TextRequest textRequest = new TextRequest("abc)");
        perform(postJson(REST_URL, textRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value("false"));
    }
}