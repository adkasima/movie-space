package com.moviespace.service;

import com.moviespace.entity.Category;
import com.moviespace.entity.Streaming;
import com.moviespace.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingRepository streamingRepository;

    public List<Streaming> findAll() {
        return streamingRepository.findAll();
    }

    public Streaming save(Streaming streaming) {
        return streamingRepository.save(streaming);
    }

    public Optional<Streaming> getById(Long id) {
        return Optional.ofNullable(streamingRepository.findById(id).orElse(null));
    }

    public void deleteById(Long id) {
        streamingRepository.deleteById(id);
    }
}
