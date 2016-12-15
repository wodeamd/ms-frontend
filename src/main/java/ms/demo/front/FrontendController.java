package ms.demo.front;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/frontend", produces = {APPLICATION_JSON_VALUE})
public class FrontendController {

    @Value("${worker.url}")
    private String workerUrl;
    @Autowired
    private ObjectMapper objectMapper;



    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(method = RequestMethod.GET)
    public String defaultPage() {
        try {
            return "This is frontend worker:" + InetAddress.getLocalHost().getHostAddress().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error happen:" + e.getMessage();
        }

    }

    @RequestMapping( value="/hex", method = RequestMethod.GET)
    public String invokeCaculation(@RequestParam(name="str", required = true) String str) {
        try {
           return convert(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error when calculating:" + e.getMessage();
        }


    }

    public String convert(String str) {
        String url = this.workerUrl + "/backend/hex?str={str}";
       return restTemplate.getForObject(url, String.class, str);
    }




}
