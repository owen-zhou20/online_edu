package com.sv.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// Level one subject vo
@Data
public class OneSubject {

    private String id;

    private String title;

    // List of children level two subject for this level one subject
    private List<TwoSubject> children = new ArrayList<>();

}
