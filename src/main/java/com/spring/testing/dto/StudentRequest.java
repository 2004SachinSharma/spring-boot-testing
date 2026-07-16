package com.spring.testing.dto;

import lombok.*;

@Data
@RequiredArgsConstructor
@Setter
@Getter
public class StudentRequest {
    private String name;
    private int age;
    private int marks;
}
