package com.ytk.todac.todac.domain.ai.prompt;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class PromptLoader {
    private final ResourceLoader resourceLoader;
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public String load(String path){
        return cache.computeIfAbsent(path, p -> {
            try {
                Resource resource = resourceLoader.getResource("classpath:" + p);
                byte[] bytes = resource.getInputStream().readAllBytes();
                return new String(bytes, StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new IllegalStateException("프롬프트 파일 로드 실패: " + p, e);
            }
        });
    }

    public String render(String path, Map<String, String> vars) {
        String template = load(path);
        for (var e : vars.entrySet()) {
            template = template.replace("{" + e.getKey() + "}", e.getValue());
        }
        return template;
    }
}
