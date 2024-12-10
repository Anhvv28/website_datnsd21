package com.fpt.niceshoes.service.chatgpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.niceshoes.service.ShoeService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class ChatGPTService {

    @Autowired
    private ShoeService shoeService;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    String promptTemplate = "Bạn là một trợ lý AI thông minh cho một cửa hàng.Bạn có tên là 'XNXX AI'." +
            "Việc của bạn là trả lời những truy vấn của khách hàng liên quan đến thông tin của của hàng, sản phẩm được bày bán,các thông tin về chủ cửa hàng, tên của bạn.Và hội thoại, trao đổi trả lời các câu hỏi của khách hàng, khách hàng sử dụng tiếng việt... . " +
            "Hãy đọc hiểu văn bản mô tả về cửa hàng và lấy thông tin từ văn bản này để trả lời cho những truy vấn của khách hàng.\\n" +
            "Sau đây là một văn bản mô tả về cửa hàng.\\n" +
            "Cửa hàng giày thể thao Inno String Stride tọa lạc tại số 69 đường Trần Duy Hưng thành phố Hà Nội cung cấp đa dạng các mẫu giày như Addias, Nike ,Bata ,Apex và Puma, phù hợp cho nhiều hoạt động thể thao khác nhau.q"+
            "Chủ của hàng tên là Việt Anh." +
            "Sản phẩm có nhiều màu sắc hấp dẫn, với kích thước từ đa dạng đến sản phẩm để đáp ứng mọi nhu cầu của khách hàng."+
            "Mỗi đôi giày được thiết kế với công nghệ 6.0  giúp tăng cường sự thoải mái và hỗ trợ tối ưu cho đôi chân."+
            "Giá cả hợp lý, dao động từ 500.000VND đến 8.000.000VND, đảm bảo chất lượng cao mà vẫn phù hợp với túi tiền." +
            "Ngoài ra, cửa hàng còn có các chương trình khuyến mãi và giảm giá đặc biệt cho các sản phẩm mới. " +
            "Inno String Stride là lựa chọn lý tưởng cho những ai tìm kiếm giày thể thao chất lượng với nhiều ưu điểm vượt trội." +
            "Inno String Stride hỗ trợ tư vấn , xử lý , giải đáp thắc mắc thông qua email anhvv28@fpt.com , hotline 0965699901 .\\n"+
            "Dựa vào văn bản trên và trả lời câu truy vấn sau, câu trả lời ngắn gọn và giải quyết đúng trọng tâm câu truy vấn. " +
            "Nếu như câu truy vấn không liên quan đến các thông tin được cung cấp trong văn bản thì trả lời 'Tôi không biết'.\\n" +
            "Câu truy vấn: %s";

    public String getChatGPTResponse(String userMessage) {
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        String model = "gpt-4o-mini";

        String prompt = String.format(promptTemplate, userMessage);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost postRequest = new HttpPost(apiUrl);

            // Set headers
            postRequest.setHeader("Authorization", "Bearer " + openaiApiKey);
            postRequest.setHeader("Content-Type", "application/json");

            // Request body
            String jsonBody = String.format(
                    "{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"max_tokens\": 150, \"temperature\": 0.5}",
                    model, prompt);

            StringEntity entity = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
            postRequest.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String responseString = reader.lines().collect(Collectors.joining());
                return extractReply(responseString);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Xin lỗi, có lỗi xảy ra khi kết nối với dịch vụ ChatGPT.";
        }
    }

    private String extractReply(String responseString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseString);
            JsonNode choicesNode = rootNode.path("choices");

            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode messageNode = choicesNode.get(0).path("message").path("content");
                return messageNode.asText().trim();
            } else {
                return "Không thể xử lý phản hồi.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Không thể xử lý phản hồi.";
        }
    }
}


