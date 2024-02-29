package srinageswari.programmedhousehold.backend.dto.common;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import srinageswari.programmedhousehold.backend.enums.Sort;

/**
 * @author smanickavasagam
 *     <p>Sort Request used for filtering
 */
@Data
@NoArgsConstructor
public class SortRequestDTO implements Serializable {

  private static final long serialVersionUID = 3194362295851723069L;

  private String key;

  private Sort direction;
}
