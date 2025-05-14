package com.moviespace.controller;

import com.moviespace.controller.request.StreamingRequest;
import com.moviespace.controller.response.StreamingResponse;
import com.moviespace.entity.Streaming;
import com.moviespace.mapper.StreamingMapper;
import com.moviespace.service.StreamingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/moviespace/streaming")
@RequiredArgsConstructor
@Tag(name = "Streaming", description = "Rota resposável pelo gerenciamento de serviços de Streaming")
public class StreamingController {

    private final StreamingService streamingService;

    @Operation(summary = "Buscar serviços de Streaming", description = "Método responsável por retornar todos os serviços de Streaming",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Retorna todos os serviços de Streaming",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StreamingResponse.class))))
    @GetMapping("/all")
    public ResponseEntity<List<StreamingResponse>> getAll() {
        List<StreamingResponse> streamings = streamingService.findAll()
                .stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList();

        return ResponseEntity.ok(streamings);
    }

    @Operation(summary = "Buscar um serviço de Streaming", description = "Método responsável por buscar um serviço de Streaming",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço de streaming encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = StreamingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Serviço de streaming não encontrado",
                    content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> getById(@PathVariable Long id) {
        return streamingService.getById(id)
                .map(streaming -> ResponseEntity.ok(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar serviço de Streaming", description = "Método responsável pelo salvamento de um serviço de Streaming",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Serviço de Streaming salvo com sucesso",
            content = @Content(schema = @Schema(implementation = StreamingResponse.class)))
    @PostMapping("/create")
    public ResponseEntity<StreamingResponse> save(@Valid @RequestBody StreamingRequest request) {
        Streaming newStreaming = StreamingMapper.toStreaming(request);
        Streaming savedStreaming = streamingService.save(newStreaming);
        return ResponseEntity.status(HttpStatus.CREATED).body(StreamingMapper.toStreamingResponse(savedStreaming));
    }

    @Operation(summary = "Atualizar  serviço de Streaming", description = "Método responsável por atualizar dados do serviço de Streaming",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço de Streaming deletado com sucesso",
                    content = @Content(schema = @Schema(implementation = StreamingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Serviço de Streaming não encontrado",
                    content = @Content())
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<StreamingResponse> update(@Valid @PathVariable Long id, @RequestBody StreamingRequest request) {
        return streamingService.update(id, StreamingMapper.toStreaming(request)).map(streaming -> ResponseEntity.ok(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar serviço de Streaming", description = "Método responsável por deletar um serviço de Streaming",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço de Streaming deletado com sucesso",
                    content = @Content()),
            @ApiResponse(responseCode = "404", description = "Serviço de Streaming não encontrado",
                    content = @Content())
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<Streaming> optStreaming = streamingService.getById(id);

        if (optStreaming.isPresent()) {
            streamingService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();

    }
}
