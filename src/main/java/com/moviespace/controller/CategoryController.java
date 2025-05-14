package com.moviespace.controller;

import com.moviespace.controller.request.CategoryRequest;
import com.moviespace.controller.response.CategoryResponse;
import com.moviespace.entity.Category;
import com.moviespace.mapper.CategoryMapper;
import com.moviespace.service.CategoryService;
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

@RestController()
@RequestMapping("/moviespace/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Rota resposável pelo gerenciamento de categorias")
public class CategoryController {

    private final CategoryService categoryService;


    @Operation(summary = "Buscar Categorias", description = "Método responsável por retornar todas as Categorias cadastradas",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Retorna todas as Categorias cadastradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))))
    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAll() {
        List<CategoryResponse> categories = categoryService.findAll()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();

        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Busca uma Categoria", description = "Método responsável por buscar uma categoria",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return categoryService.getById(id)
                .map(category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar Categoria", description = "Método responsável pelo salvamento de uma Categoria",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Categoria salva com sucesso",
            content = @Content(schema = @Schema(implementation = CategoryResponse.class)))
    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest request) {
        Category newCategory = CategoryMapper.toCategory(request);
        Category savedCategory = categoryService.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toCategoryResponse(savedCategory));
    }

    @Operation(summary = "Atualiza uma Categoria", description = "Método responsável por atualizar uma categoria",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content())
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponse> update(@Valid @PathVariable Long id, @RequestBody CategoryRequest request) {
        return categoryService.update(id, CategoryMapper.toCategory(request))
                .map(category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());

    }

    @Operation(summary = "Deleta uma Categoria", description = "Método responsável por deletar uma categoria",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria deletada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content())
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<Category> optCategory = categoryService.getById(id);

        if (optCategory.isPresent()) {
            categoryService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }

}
