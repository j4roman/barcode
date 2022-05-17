package com.example.j4roman.barcode.service.utils.tasks;

import com.example.j4roman.barcode.persistance.entities.Action;

/**
 * Performs generation and parsing for {@link Action}
 *
 */

public interface Task {

    void generate(StringBuilder sb);
    void parse(StringBuilder sb);
}
