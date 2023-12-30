package srinageswari.programmedhousehold.model;

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
public class Itemtype {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long typeId;

  @NotNull
  @Column(unique = true)
  private String type;

  @OneToMany(mappedBy = "itemtype")
  private Set<Item> items;

  public void setTypeId(Long id) {
    this.typeId = id;
  }

  public Itemtype(Long typeId) {
    this.typeId = typeId;
  }

  public Itemtype(Long typeId, String type) {
    this.typeId = typeId;
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Itemtype that = (Itemtype) o;
    return type.equals(that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }
}
