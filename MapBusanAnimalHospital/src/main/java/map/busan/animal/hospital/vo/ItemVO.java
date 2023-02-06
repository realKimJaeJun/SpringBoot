package map.busan.animal.hospital.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemVO {
    private String gugun;
    private String lon;
    private String approval;
    private String road_address;
    private String tel;
    private String lat;
    private String animal_hospital;
    private String basic_date;
}
