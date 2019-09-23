package eu.melodic.vassilis.staff;
 
import java.time.Instant;
import java.util.concurrent.TimeUnit;
 
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
 
@Measurement(name = "ComponentTime", timeUnit = TimeUnit.SECONDS)
public class ComponMeasurement {
 
    @Column(name = "time")
    private Instant time;
 
    @Column(name = "ComponentName")
    private String ComponentName;
 
    @Column(name = "timeDepl")
    private double timeDepl;
 
    public Instant getTime() {
        return time;
    }
 
    public void setTime(Instant time) {
        this.time = time;
    }
 
    public String getComponentName() {
        return ComponentName;
    }
 
    public void setComponentName(String ComponentName) {
        this.ComponentName = ComponentName;
    }
 
    public double timeDepl() {
        return timeDepl;
    }
 
    public void timeDepl(double timeDepl) {
        this.timeDepl = timeDepl;
    }
	
	public String toString() { return timeDepl+""; }
}