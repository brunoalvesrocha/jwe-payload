package com.example.demojwe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author brunorocha
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {

    private String nome;
    private String idade;

    public Info createInfo() {
        return new Info("Bruno", "16");
    }
}
