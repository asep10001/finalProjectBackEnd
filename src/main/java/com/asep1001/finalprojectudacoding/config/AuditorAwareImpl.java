package com.asep1001.finalprojectudacoding.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(Arrays.asList("Admin", "Asep", "Agus", "Heri")
                .get(new Random().nextInt(4)));
    }
}
