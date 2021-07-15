
package com.wether.news.Api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Weather {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("current")
    @Expose
    private Current current;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public static class Current {

        @SerializedName("last_updated_epoch")
        @Expose
        private Integer lastUpdatedEpoch;
        @SerializedName("last_updated")
        @Expose
        private String lastUpdated;
        @SerializedName("temp_c")
        @Expose
        private Double tempC;
        @SerializedName("temp_f")
        @Expose
        private Double tempF;
        @SerializedName("is_day")
        @Expose
        private Integer isDay;
        @SerializedName("condition")
        @Expose
        private Condition condition;
        @SerializedName("wind_mph")
        @Expose
        private Double windMph;
        @SerializedName("wind_kph")
        @Expose
        private Double windKph;
        @SerializedName("wind_degree")
        @Expose
        private Integer windDegree;
        @SerializedName("wind_dir")
        @Expose
        private String windDir;
        @SerializedName("pressure_mb")
        @Expose
        private Double pressureMb;
        @SerializedName("pressure_in")
        @Expose
        private Double pressureIn;
        @SerializedName("precip_mm")
        @Expose
        private Double precipMm;
        @SerializedName("precip_in")
        @Expose
        private Double precipIn;
        @SerializedName("humidity")
        @Expose
        private Integer humidity;
        @SerializedName("cloud")
        @Expose
        private Integer cloud;
        @SerializedName("feelslike_c")
        @Expose
        private Double feelslikeC;
        @SerializedName("feelslike_f")
        @Expose
        private Double feelslikeF;
        @SerializedName("vis_km")
        @Expose
        private Double visKm;
        @SerializedName("vis_miles")
        @Expose
        private Double visMiles;
        @SerializedName("uv")
        @Expose
        private Double uv;
        @SerializedName("gust_mph")
        @Expose
        private Double gustMph;
        @SerializedName("gust_kph")
        @Expose
        private Double gustKph;
        @SerializedName("air_quality")
        @Expose
        private AirQuality airQuality;

        public Integer getLastUpdatedEpoch() {
            return lastUpdatedEpoch;
        }

        public void setLastUpdatedEpoch(Integer lastUpdatedEpoch) {
            this.lastUpdatedEpoch = lastUpdatedEpoch;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public Double getTempC() {
            return tempC;
        }

        public void setTempC(Double tempC) {
            this.tempC = tempC;
        }

        public Double getTempF() {
            return tempF;
        }

        public void setTempF(Double tempF) {
            this.tempF = tempF;
        }

        public Integer getIsDay() {
            return isDay;
        }

        public void setIsDay(Integer isDay) {
            this.isDay = isDay;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public Double getWindMph() {
            return windMph;
        }

        public void setWindMph(Double windMph) {
            this.windMph = windMph;
        }

        public Double getWindKph() {
            return windKph;
        }

        public void setWindKph(Double windKph) {
            this.windKph = windKph;
        }

        public Integer getWindDegree() {
            return windDegree;
        }

        public void setWindDegree(Integer windDegree) {
            this.windDegree = windDegree;
        }

        public String getWindDir() {
            return windDir;
        }

        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        public Double getPressureMb() {
            return pressureMb;
        }

        public void setPressureMb(Double pressureMb) {
            this.pressureMb = pressureMb;
        }

        public Double getPressureIn() {
            return pressureIn;
        }

        public void setPressureIn(Double pressureIn) {
            this.pressureIn = pressureIn;
        }

        public Double getPrecipMm() {
            return precipMm;
        }

        public void setPrecipMm(Double precipMm) {
            this.precipMm = precipMm;
        }

        public Double getPrecipIn() {
            return precipIn;
        }

        public void setPrecipIn(Double precipIn) {
            this.precipIn = precipIn;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public Integer getCloud() {
            return cloud;
        }

        public void setCloud(Integer cloud) {
            this.cloud = cloud;
        }

        public Double getFeelslikeC() {
            return feelslikeC;
        }

        public void setFeelslikeC(Double feelslikeC) {
            this.feelslikeC = feelslikeC;
        }

        public Double getFeelslikeF() {
            return feelslikeF;
        }

        public void setFeelslikeF(Double feelslikeF) {
            this.feelslikeF = feelslikeF;
        }

        public Double getVisKm() {
            return visKm;
        }

        public void setVisKm(Double visKm) {
            this.visKm = visKm;
        }

        public Double getVisMiles() {
            return visMiles;
        }

        public void setVisMiles(Double visMiles) {
            this.visMiles = visMiles;
        }

        public Double getUv() {
            return uv;
        }

        public void setUv(Double uv) {
            this.uv = uv;
        }

        public Double getGustMph() {
            return gustMph;
        }

        public void setGustMph(Double gustMph) {
            this.gustMph = gustMph;
        }

        public Double getGustKph() {
            return gustKph;
        }

        public void setGustKph(Double gustKph) {
            this.gustKph = gustKph;
        }

        public AirQuality getAirQuality() {
            return airQuality;
        }

        public void setAirQuality(AirQuality airQuality) {
            this.airQuality = airQuality;
        }




        public static class Condition {

            @SerializedName("text")
            @Expose
            private String text;
            @SerializedName("icon")
            @Expose
            private String icon;
            @SerializedName("code")
            @Expose
            private Integer code;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public Integer getCode() {
                return code;
            }

            public void setCode(Integer code) {
                this.code = code;
            }

        }



        public static class AirQuality {

            @SerializedName("co")
            @Expose
            private Double co;
            @SerializedName("no2")
            @Expose
            private Double no2;
            @SerializedName("o3")
            @Expose
            private Double o3;
            @SerializedName("so2")
            @Expose
            private Double so2;
            @SerializedName("pm2_5")
            @Expose
            private Double pm25;
            @SerializedName("pm10")
            @Expose
            private Double pm10;
            @SerializedName("us-epa-index")
            @Expose
            private Integer usEpaIndex;
            @SerializedName("gb-defra-index")
            @Expose
            private Integer gbDefraIndex;

            public Double getCo() {
                return co;
            }

            public void setCo(Double co) {
                this.co = co;
            }

            public Double getNo2() {
                return no2;
            }

            public void setNo2(Double no2) {
                this.no2 = no2;
            }

            public Double getO3() {
                return o3;
            }

            public void setO3(Double o3) {
                this.o3 = o3;
            }

            public Double getSo2() {
                return so2;
            }

            public void setSo2(Double so2) {
                this.so2 = so2;
            }

            public Double getPm25() {
                return pm25;
            }

            public void setPm25(Double pm25) {
                this.pm25 = pm25;
            }

            public Double getPm10() {
                return pm10;
            }

            public void setPm10(Double pm10) {
                this.pm10 = pm10;
            }

            public Integer getUsEpaIndex() {
                return usEpaIndex;
            }

            public void setUsEpaIndex(Integer usEpaIndex) {
                this.usEpaIndex = usEpaIndex;
            }

            public Integer getGbDefraIndex() {
                return gbDefraIndex;
            }

            public void setGbDefraIndex(Integer gbDefraIndex) {
                this.gbDefraIndex = gbDefraIndex;
            }

        }

    }

    public static class Location {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lon")
        @Expose
        private Double lon;
        @SerializedName("tz_id")
        @Expose
        private String tzId;
        @SerializedName("localtime_epoch")
        @Expose
        private Integer localtimeEpoch;
        @SerializedName("localtime")
        @Expose
        private String localtime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public String getTzId() {
            return tzId;
        }

        public void setTzId(String tzId) {
            this.tzId = tzId;
        }

        public Integer getLocaltimeEpoch() {
            return localtimeEpoch;
        }

        public void setLocaltimeEpoch(Integer localtimeEpoch) {
            this.localtimeEpoch = localtimeEpoch;
        }

        public String getLocaltime() {
            return localtime;
        }

        public void setLocaltime(String localtime) {
            this.localtime = localtime;
        }

    }



}
