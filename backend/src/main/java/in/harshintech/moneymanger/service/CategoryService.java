package in.harshintech.moneymanger.service;

import in.harshintech.moneymanger.dto.CategoryDTO;
import in.harshintech.moneymanger.entity.CategoryEntity;
import in.harshintech.moneymanger.entity.ProfileEntity;
import in.harshintech.moneymanger.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ProfileService profileService;
    private final CategoryRepository categoryRepository;



    //Save category
    public CategoryDTO saveCategory(CategoryDTO categoryDTO){
        ProfileEntity profile = profileService.getCurrentProfile();
        if(categoryRepository.existsByNameAndProfileId(categoryDTO.getName(),profile.getId())){
            throw new RuntimeException("Category with this name is already exists");
        }

        CategoryEntity newCategory = toEntity(categoryDTO,profile);
        newCategory = categoryRepository.save(newCategory);
        return toDTO(newCategory);
    }

    //get categories for current user
    public List<CategoryDTO> getCategoriesForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntity> categories = categoryRepository.findByProfileId(profile.getId());
        return categories.stream().map(this::toDTO).toList();

        /**
        same as above return
        this::toDTO <===> category -> this.toDTO(category)
        return categories.stream().map(category -> toDTO(category)).toList();

        JS: array.map()
        Java: array → stream → map() → collect


        categories.stream().map(this::toDTO);
        ---> What happens here?
        No DTO created
        No loop runs
        Nothing happens
        It just creates a pipeline (plan)
        Stream is lazy ==> I know HOW to process… but I will NOT do it until you ask for result.

         Real-Life Analogy
         👉 Stream = food order 🍔
         👉 .map() = cooking steps
         But food is NOT made until you:
         👉 .toList() = “Serve the food”

         🔥 One-Line Final Understanding
         👉 .toList() is required because it executes the stream and collects the result

         After conversion, your list will look like:
         Example :
         List<CategoryDTO> categories = List.of(
         new CategoryDTO(1L, "Food", "EXPENSE"),
         new CategoryDTO(2L, "Salary", "INCOME"),
         new CategoryDTO(3L, "Travel", "EXPENSE"),
         new CategoryDTO(4L, "Shopping", "EXPENSE"),
         new CategoryDTO(5L, "Freelance", "INCOME")
         );
        */
    }

    //get categories by type for current user
    public List<CategoryDTO> getCategoriesByTypeForCurrentUser(String type){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntity> entities = categoryRepository.findByTypeAndProfileId(type,profile.getId());
        return entities.stream().map(this::toDTO).toList();
    }

    //update category
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO dto){
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity existingCategory = categoryRepository.findByIdAndProfileId(categoryId, profile.getId())
                .orElseThrow(() -> new RuntimeException("Category not found or not accessible"));
        existingCategory.setName(dto.getName());
        existingCategory.setType(dto.getType());
        existingCategory.setIcon((dto.getIcon()));
        existingCategory = categoryRepository.save(existingCategory);
        return toDTO(existingCategory);
    }

    //helper method
    private CategoryEntity toEntity(CategoryDTO categoryDTO, ProfileEntity profile){
        return CategoryEntity.builder()
                .name(categoryDTO.getName())
                .icon(categoryDTO.getIcon())
                .profile(profile)
                .type(categoryDTO.getType())
                .build();


        //Entity need full profile object and DTO need only profileId in our case remember this thing;
    }

    private CategoryDTO toDTO(CategoryEntity entity){
        return CategoryDTO.builder()
                .id(entity.getId())
                .profileId(entity.getProfile() != null ? entity.getProfile().getId() : null)
                .name(entity.getName())
                .icon(entity.getIcon())
                .createdAt(entity.getCreatedAt())
                .updateAt(entity.getUpdateAt())
                .type(entity.getType())
                .build();
    }
}
