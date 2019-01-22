package ar.test.meli.mutants.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Configuration
@ConfigurationProperties(prefix = "app")
@Validated
public class ApplicationProperties {

    @NestedConfigurationProperty
    @Valid
    private final Detection detection = new Detection();

    @NestedConfigurationProperty
    @Valid
    private final Stats stats = new Stats();

    public static class Detection {

        @NotBlank
        private String mutantMessage;

        @NotBlank
        private String notMutantMessage;

        @Min(2)
        @Max(Integer.MAX_VALUE)
        private int minNbToVerifyMutant = 4;

        @Min(2)
        @Max(180)
        private int maxNbSequenceLength = 180;

        public String getMutantMessage() {
            return mutantMessage;
        }

        public void setMutantMessage(String mutantMessage) {
            this.mutantMessage = mutantMessage;
        }

        public String getNotMutantMessage() {
            return notMutantMessage;
        }

        public void setNotMutantMessage(String notMutantMessage) {
            this.notMutantMessage = notMutantMessage;
        }

        public int getMinNbToVerifyMutant() {
            return minNbToVerifyMutant;
        }

        public void setMinNbToVerifyMutant(int minNbToVerifyMutant) {
            this.minNbToVerifyMutant = minNbToVerifyMutant;
        }

        public int getMaxNbSequenceLength() {
            return maxNbSequenceLength;
        }

        public void setMaxNbSequenceLength(int maxNbSequenceLength) {
            this.maxNbSequenceLength = maxNbSequenceLength;
        }
    }

    public static class Stats {

        @Min(0)
        @Max(Short.MAX_VALUE)
        private short ratioDecimalPlaces;

        public short getRatioDecimalPlaces() {
            return ratioDecimalPlaces;
        }

        public void setRatioDecimalPlaces(Short ratioDecimalPlaces) {
            this.ratioDecimalPlaces = ratioDecimalPlaces;
        }
    }

    public Detection getDetection() {
        return this.detection;
    }

    public Stats getStats() {
        return stats;
    }
}
