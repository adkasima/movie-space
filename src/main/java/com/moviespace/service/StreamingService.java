package com.moviespace.service;

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

    public Optional<Streaming> update(Long id, Streaming updateStreaming) {
        Optional<Streaming> optStreaming = streamingRepository.findById(id);

        if (optStreaming.isPresent()) {
            Streaming streaming = optStreaming.get();

            streaming.setName(updateStreaming.getName());
            streamingRepository.save(streaming);
            return Optional.of(streaming);
        }
        return Optional.empty();
    }

    public Streaming save(Streaming streaming) {
        return streamingRepository.save(streaming);
    }

    public Optional<Streaming> getById(Long id) {
        return streamingRepository.findById(id);
    }

    public void deleteById(Long id) {
        streamingRepository.deleteById(id);
    }
}
