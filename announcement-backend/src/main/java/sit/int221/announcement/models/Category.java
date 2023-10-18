package sit.int221.announcement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "category")
@NoArgsConstructor
public class Category {

    @Id
    private Integer categoryId;
    private String categoryName;

    public Category(Integer categoryId) { this.categoryId = categoryId; }

}
