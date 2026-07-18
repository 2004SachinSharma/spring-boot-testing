package com.spring.testing.controller;

import com.spring.testing.Entity.Student;
import com.spring.testing.dto.StudentRequest;
import com.spring.testing.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import tools.jackson.databind.ObjectMapper;

/**
 * <h2>StudentControllerTest - Web Slice Test Class</h2>
 * <p>
 * Yeh class {@code StudentController} ki unit testing ke liye banayi gayi hai.
 * Yeh ek <b>Web Slice Test</b> hai, jiska matlab hai ki yeh poore Spring Boot
 * Application Context (Database, heavy services, etc.) ko load nahi karega.
 * </p>
 *
 * <h3>Key Concepts Covered:</h3>
 * <ul>
 *   <li><b>Web MVC Testing:</b> Sirf Web layer (Controllers, Filters, Converters) ko load karna.</li>
 *   <li><b>Mocking:</b> External service dependencies ko control karne ke liye Mockito use karna.</li>
 *   <li><b>AAA Pattern:</b> Test logic ko Arrange, Act, aur Assert phases mein organize karna.</li>
 * </ul>
 *
 * @author Sachin Sharma
 * @version 1.0
 * @see StudentController
 */
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    /**
     * <h3>MockMvc Instance</h3>
     * <p>
     * Yeh Spring MVC testing framework ka main entry point hai.
     * </p>
     * <ul>
     *   <li><b>Kaise kaam karta hai:</b> Yeh bina actual Tomcat Server start kiye,
     *   internal Spring dispatcher servlet par HTTP requests direct bhej sakta hai.</li>
     *   <li><b>Injection:</b> Isko {@code @WebMvcTest} automatically configure aur inject karta hai.</li>
     * </ul>
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * <h3>Mockito Mock Service Bean</h3>
     * <p>
     * Yeh annotation Spring Context mein {@code StudentService} ka ek nakli (Mock) object register karta hai.
     * </p>
     * <ul>
     *   <li><b>Kyun use kiya:</b> Kyunki yeh ek slice test hai, isliye real database ya service available nahi hote.</li>
     *   <li><b>Fayda:</b> Hum is mock object ko bta sakte hain ki controller ke call karne par ise kya return karna hai.</li>
     *   <li><i>Note:</i> Yeh Spring Boot 3.4+ ka modern annotation hai jo purane {@code @MockBean} ki jagah aya hai.</li>
     * </ul>
     * <p>Yaha pe StudentService bean banega, aur inject ho jayega StudentController bean me jo ki context me bean bana h usme jo
     * {@code @WebMvcTest(StudentController.class)} me bana h </p>
     */
    @MockitoBean
    private StudentService service;

    /**
     * <h3>Jackson ObjectMapper Instance</h3>
     * <p>
     * Yeh object serialization aur deserialization ke kaam aata hai.
     * </p>
     * <ul>
     *   <li><b>Kaam:</b> Java Objects ko JSON string mein badalna (aur iska ulta karna).</li>
     *   <li><b>Kyun zaroorat hai:</b> HTTP POST request bhejte waqt payload body JSON format mein hi honi chahiye.</li>
     * </ul>
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * <h3>Test Case: Success Scenario for Student Creation</h3>
     * <p>
     * Yeh method check karta hai ki jab valid data ke saath {@code POST /students} request aati hai,
     * to controller use sahi se process karke HTTP 201 (Created) status aur sahi JSON data return karta hai ya nahi.
     * </p>
     *
     * @throws Exception Agar request trigger karne mein ya JSON parsing mein koi dikkat aaye.
     */
    @Test
    @DisplayName("POST /students - Create Student Successfully")
    void testCreateStudentSuccess() throws Exception {

        // ==========================================
        // PHASE 1: ARRANGE (Setup Test Data & Mocks)
        // ==========================================

        // Controller ke liye dummy input request body taiyar ki
        StudentRequest request = new StudentRequest();
        request.setName("Sachin");
        request.setAge(21);
        request.setMarks(90);

        // Service se expected mock response payload design kiya (isme ID bhi mocked hai)
        Student response = Student.builder()
                .id(1L)
                .name("Sachin")
                .age(21)
                .marks(90)
                .build();

        /*
         * Stubbing Behavior:
         * Mockito ko instruction di ja rahi hai ki:
         * "JAB (when) service ka saveStudent() method call ho kisi bhi StudentRequest type ke object ke sath,
         * TO (thenReturn) bina kisi database ke, direct hamara banaya hua 'response' object return kar do."
         */
        when(service.saveStudent(any(StudentRequest.class)))
                .thenReturn(response);

        // ==========================================
        // PHASE 2 & 3: ACT & ASSERT (Execution & Validation)
        // ==========================================

        mockMvc.perform(
                        /*
                         * 1. Request Builder:
                         * '/students' path par ek HTTP POST request create karo.
                         */
                        post("/students")

                                /*
                                 * 2. Header Configuration:
                                 * Content-Type ko JSON set karo taaki controller ko pta chale ki payload JSON format mein hai.
                                 */
                                .contentType(MediaType.APPLICATION_JSON)

                                /*
                                 * 3. Request Body (Payload):
                                 * ObjectMapper ki madad se Java 'request' object ko JSON string format mein badal kar body mein bheja.
                                 * E.g., '{"name":"Sachin","age":21,"marks":90}'
                                 */
                                .content(objectMapper.writeValueAsString(request))
                )
                /*
                 * 4. Http Status Assertion:
                 * Check karo ki response ka HTTP Status code 201 (Created) hai ya nahi.
                 */
                .andExpect(status().isCreated())

                /*
                 * 5. JSON Response Body Assertions (using JsonPath):
                 * Response ke JSON tree ke root ($) elements ko evaluate karo.
                 * Check karo ki id=1, name='Sachin', age=21, aur marks=90 hi lautaye gaye hain.
                 */
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sachin"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.marks").value(90));

        // ==========================================
        // PHASE 4: VERIFY (Double Check Interactions)
        // ==========================================

        /*
         * Safety Check:
         * Mockito verify se yeh pakka kiya ja raha hai ki pure flow ke dauran
         * 'service.saveStudent()' method background mein EXACTLY ONE TIME (times(1)) chalna chahiye.
         * Agar yeh nahi chala ya ek se zyada baar chala, to test fail ho jayega.
         */
        verify(service, times(1))
                .saveStudent(any(StudentRequest.class));
    }

    /**
     * [ @WebMvcTest ]  ----> Creates a sliced web container (No DB, No heavy beans)
     *       |
     *       |-- Injecting dependencies using...
     *       v
     * [ @MockBean ]    ----> Spies/Mocks the real Service layer beans inside the container
     *       |
     *       |-- Triggering the HTTP web layer using...
     *       v
     * [ MockMvc ]      ----> Acts as a fake browser to perform API calls (post, get, delete)
     *       |
     *       |-- Receiving and validating the raw server output using...
     *       v
     * [ Response Assertions ] -> Asserts meta-data like status().isCreated() or status().isOk()
     *       |
     *       |-- Inspecting the deep inner payload fields using...
     *       v
     * [ jsonPath() ]   ----> Traverses the JSON body structure (e.g., $.name) to check final values
     *
     * ⚡ Punchy Quick-Reference List@WebMvcTest: Configures only web layer components.MockMvc: Simulates HTTP requests without server overhead.@MockBean: Replaces real Spring beans with Mockito mocks.jsonPath(): Evaluates JSON expression matches against response body.Response Assertions: Verifies global properties like status, headers, and type.*/

}
