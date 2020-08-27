package com.redlogic.utils.distance;

import java.util.List;

public class DistanceResponse {

    /**
     * destination_addresses : ["Unnamed Road, Kadappasandampatti, Tamil Nadu 635206, India"]
     * origin_addresses : ["Unnamed Road, Chikka Halli, Talamalai R.F., Tamil Nadu 638461, India"]
     * rows : [{"elements":[{"distance":{"text":"250 km","value":249513},"duration":{"text":"5 hours 49 mins","value":20910},"status":"OK"}]}]
     * status : OK
     */

    private String status;
    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<RowsBean> rows;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(List<String> destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public List<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(List<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private List<ElementsBean> elements;

        public List<ElementsBean> getElements() {
            return elements;
        }

        public void setElements(List<ElementsBean> elements) {
            this.elements = elements;
        }

        public static class ElementsBean {
            /**
             * distance : {"text":"250 km","value":249513}
             * duration : {"text":"5 hours 49 mins","value":20910}
             * status : OK
             */

            private DistanceBean distance;
            private DurationBean duration;
            private String status;

            public DistanceBean getDistance() {
                return distance;
            }

            public void setDistance(DistanceBean distance) {
                this.distance = distance;
            }

            public DurationBean getDuration() {
                return duration;
            }

            public void setDuration(DurationBean duration) {
                this.duration = duration;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public static class DistanceBean {
                /**
                 * text : 250 km
                 * value : 249513
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class DurationBean {
                /**
                 * text : 5 hours 49 mins
                 * value : 20910
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }
        }
    }
}

