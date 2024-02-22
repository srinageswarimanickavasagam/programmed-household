package srinageswari.programmedhousehold.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "itemtype")
public class ItemtypeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long typeId;

  @NotNull
  @Column(unique = true)
  private String type;

  private int storageLife;

  private boolean freshFridge;

  @OneToMany(mappedBy = "itemtype")
  private Set<ItemEntity> items;

  public void setTypeId(Long id) {
    this.typeId = id;
  }

  public ItemtypeEntity(Long typeId) {
    this.typeId = typeId;
  }

  public ItemtypeEntity(Long typeId, String type) {
    this.typeId = typeId;
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ItemtypeEntity that = (ItemtypeEntity) o;
    return type.equals(that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }
}
