package srinageswari.programmedhousehold.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appuser")
public class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  String email;
  String name;
  String provider;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  @PrePersist
  void assignCreatedAt() {
    this.createdAt = LocalDateTime.now();
  }

  public AppUser(Long userId, String email, String name, String imageUrl, String provider) {
    this.userId = userId;
    this.email = email;
    this.name = name;
    this.provider = provider;
  }

  public AppUser(Long userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AppUser)) return false;
    AppUser appUser = (AppUser) o;
    return getUserId() != null && Objects.equals(getUserId(), appUser.getUserId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
