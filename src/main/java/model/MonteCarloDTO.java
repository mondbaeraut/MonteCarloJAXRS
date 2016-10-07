package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Niklas on 06/10/16.
 */
@XmlRootElement(name = "Response")
public class MonteCarloDTO {
    double pi;
    long duration;

    @XmlElement(name = "PI")

    public double getPi() {
        return pi;
    }

    public void setPi(double pi) {
        this.pi = pi;
    }
    @XmlElement(name="Duration")

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
