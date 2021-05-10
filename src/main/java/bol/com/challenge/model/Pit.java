package bol.com.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ehsan Sh
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Pit {

    private Integer id;
    private Integer stones;

    public void sow () {
        this.stones++;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.stones==0;
    }

    public void addStones(Integer i) {
        this.stones=stones+i;
    }

    @Override
    public String toString() {
        return  id.toString() +
                ":" +
                stones.toString() ;
    }
}
