package map.busan.animal.hospital.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultVO {
    private GetTblAnimalHospital getTblAnimalHospital;

    @Data
    public class GetTblAnimalHospital{
        private Header header;
        private Body body;

        @Data
        public class Header{
            private String resultMsg;
            private String resultCode;
        }

        @Data
        public class Body{
            private Items items;
            private int numOfRows;
            private int pageNo;
            private int totalCount;

            @Data
            public class Items{
                private List<ItemVO> item;
            }
        }
    }
}
