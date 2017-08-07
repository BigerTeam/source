package com.source.utils.train;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SearchResultChangeMethod {
    private boolean isDebug = true;

    /**
     * 将12306新版查询结果解析成旧版查询结果，兼容12306升级
     * 
     * @param newSearchResult
     * @return
     * @time 2017年4月25日 下午9:08:44
     * @author fiend
     */
    public String changeSearchResult(String newSearchResult) {
        String oldSearchResult = "";
        try {
            if (newSearchResult.contains("\"result\":[")) {
                JSONObject parseObject = JSONObject.parseObject(newSearchResult);
                JSONObject object = parseObject.getJSONObject("data");
                JSONArray jsonArray = object.getJSONArray("result");
                JSONObject oldObject = new JSONObject();
                oldObject.put("validateMessagesShowId", parseObject.getString("validateMessagesShowId"));
                oldObject.put("status", parseObject.getString("status"));
                oldObject.put("httpstatus", parseObject.getString("httpstatus"));
                JSONArray oldjsonArray = new JSONArray();
                for (Object obj : jsonArray) {
                    JSONObject newJsonObject = new JSONObject();
                    JSONObject jsonObject = new JSONObject();
                    if (obj instanceof String) {
                        String res = (String) obj;
                        String[] strings = res.split("\\|");
                        jsonObject.put("secretStr", strings[0]);
                        jsonObject.put("buttonTextInfo", strings[1]);
                        // 没有问题
                        newJsonObject.put("train_no", strings[2]);
                        newJsonObject.put("station_train_code", strings[3]);
                        newJsonObject.put("start_station_telecode", strings[4]);
                        newJsonObject.put("end_station_telecode", strings[5]);
                        newJsonObject.put("from_station_telecode", strings[6]);
                        newJsonObject.put("to_station_telecode", strings[7]);
                        newJsonObject.put("start_time", strings[8]);
                        newJsonObject.put("arrive_time", strings[9]);
                        newJsonObject.put("lishi", strings[10]);
                        newJsonObject.put("canWebBuy", strings[11]);
                        //不足35个就说明没办法买票，就跳过
                        if (strings.length < 35) {
                        }
                        else {
                            newJsonObject.put("lishiValue", strings[10]);
                            newJsonObject.put("yp_info", strings[12]);
                            newJsonObject.put("control_train_day", strings[14]);
                            newJsonObject.put("start_train_date", strings[13]);
                            newJsonObject.put("seat_feature", strings[14]);
                            newJsonObject.put("yp_ex", strings[33]);
                            newJsonObject.put("seat_types", strings[34]);
                            newJsonObject.put("train_seat_feature", strings[11]);
                            newJsonObject.put("train_type_code", strings[11]);
                            newJsonObject.put("start_province_code", strings[16]);
                            newJsonObject.put("start_city_code", strings[16]);
                            newJsonObject.put("end_province_code", strings[17]);
                            newJsonObject.put("end_city_code", strings[17]);
                            newJsonObject.put("location_code", strings[15]);
                            newJsonObject.put("from_station_no", strings[16]);
                            newJsonObject.put("to_station_no", strings[17]);
                            newJsonObject.put("is_support_card", strings[18]);
                            newJsonObject.put("controlled_train_flag", strings[19]);
                            newJsonObject.put("gg_num", strings[20]);
                            newJsonObject.put("gr_num", strings[21]);
                            newJsonObject.put("qt_num", strings[22]);
                            newJsonObject.put("rw_num", strings[23]);
                            newJsonObject.put("rz_num", strings[24]);
                            newJsonObject.put("tz_num", strings[25]);
                            newJsonObject.put("wz_num", strings[26]);
                            newJsonObject.put("yb_num", strings[27]);
                            newJsonObject.put("yw_num", strings[28]);
                            newJsonObject.put("yz_num", strings[29]);
                            newJsonObject.put("ze_num", strings[30]);
                            newJsonObject.put("zy_num", strings[31]);
                            newJsonObject.put("swz_num", strings[32]);
                            newJsonObject.put("dw_num", strings[33]);
                        }
                        jsonObject.put("queryLeftNewDTO", newJsonObject);
                        oldjsonArray.add(jsonObject);
                    }
                }
                oldObject.put("data", oldjsonArray);
                oldSearchResult = oldObject.toString();
                if (isDebug) {
                    WriteLog.write("SearchResultChangeMethod_changeSearchResult", oldSearchResult + "--->"
                            + newSearchResult);
                }
            }
            else {
                oldSearchResult = newSearchResult;
            }
        }
        catch (Exception e) {
            oldSearchResult = newSearchResult;
        }
        return oldSearchResult;
    }

    public static void main11(String[] args) {
        String str = "{\"validateMessagesShowId\":\"_validatorMessage\",\"status\":true,\"httpstatus\":200,\"data\":{\"result\":[\"xUmP0qLIsFZjwzh%2F%2F7aAPfLGCt89kR32GbQaUFwjGIvghu%2BaM%2FFpnrhFTZ7SZ5Jkbdfwu01E9gw%2F%0AIos%2FqnbGLYv1zhFcKUtyllLU5jsSIxULw2NipAGaEl%2Fq3B5o7uijjxuGmlM2FxycMtYBFOuf1GWh%0ANPkVbu09Ly1opQTeFMpcYBB5KIKEWhKph9dXOpoP5bDeGVV4phvnyHTNxK%2FOWHQ6qnpqGaijLpA5%0AaymFdUDjthxJDZpNEQ%3D%3D|预订|330000K5980T|K599|BTC|GZQ|BXP|GZQ|05:14|11:12|29:58|Y|vJ3X0UhnebPVzxi0UHKPx%2BhpFU8Th3ckgByWEmpFRjXOGk5S6DRIHFWL93c%3D|20170623|3|C1|09|35|0|0||||9|||无||无|无|||||10401030|1413\",\"kNanEcedT5V698FojK7TWTmDz6tCOvz9tbBJbXJy44SIEIZVSJdNA1QcQaFepnxkROiqdDzhgV5I%0A4Fl%2BS7KQFw7xsXeft0W5N%2BWGk22zDXqoM03NU%2BGXa7Rx%2Bk4r4JSj6XSma0jxI9hbLOE%2BfnK2T2m3%0A52rPA9PiwKu%2F7UOVTZFhjPT3nTTj%2BQXiTlxyL2ln63QeXfdrkWvwwSZF%2F%2FPcHGkiSNaBhuWdxYjC%0AKbnN4Qc%3D|预订|2400000G710E|G71|BXP|NZQ|BXP|IZQ|07:27|17:13|09:46|Y|iY%2FBQbfkz0VeWg9HpJIOd9wyOfzsQylcVSuLBQo983urICGL|20170624|3|P2|01|17|1|0|||||||||||有|有|18||O0M090|OM9\",\"J1oh07hK4OHD%2BUPQIGSLlMQCM696Yj9q4sSAN5ub1yWRceYE%2F7MO23JNt8%2BYwOo4uS0czzMlgU4j%0ALPbx0Cg1POF2dg7V6PLvBe%2FvOHhSasNCqJB0ixbtkimottMdMA7PBt2SNsbaH0%2BqZI%2FbUTQZvWK1%0ADHSHQ8dr2wY2SIoEg2kIEpGw%2BWe5UyFcFam3HRGqXGwoHecjEw0b%2FySs2chs3iHjppbut1OKit1v%0ADQ3S5BU%3D|预订|2400000G7920|G79|BXP|NZQ|BXP|IZQ|10:00|18:01|08:01|Y|AG4k62CYWmWm7TS1t33EqYzy7RexYOGNqz2pgr7l5YsqF5%2Bq|20170624|3|P4|01|06|1|0|||||||||||有|有|2||O0M090|OM9\",\"WChIUTcyuMKBH%2FGSl93nPB6Od6hML5Ine1Q1eTT1hceq3JuAr3BaI3bo%2FaeA6WAEQJ%2B8Y30oO3vM%0AmLb4H%2B0vZjYMyHAWpFfv4kJVkBAbwcj3rgwSN%2BidsETl8yQ9bimNRAInVguTVhV9U%2BTYaqQDO7VJ%0A6%2B0Mt0F4zrJv4fT0xftV9T8ZFvy9%2FufMxVXRNkh7Zitl6foSlAjzLk%2BLhKMiNmpCAw%2FXfJ8RDc5k%0AvL3HxlA%3D|预订|2400000G650C|G65|BXP|IZQ|BXP|IZQ|10:33|20:16|09:43|Y|Nx0oOEmdlP8DfEZ7%2F3XTaC4DYK731vRB9Zj6t4olXVlYe3zb|20170624|3|P4|01|17|1|0|||||||||||有|16|15||O0M090|OM9\",\"9N0wKWVEh3VO8x9vHWwHn2yPpkrfH0TStdfJAhNhTxIkmfsfs9w8DQoEdPBKZZNptdle8nVgu1lD%0AFnH4wkuRZAS%2FHSU9Zlt4ZeIZj1IFDDVtdVSY8RaOc2AIY7ADN7gSzy5XKrmgw2Z0rfZI56Zx7Elb%0AP%2FfR9nyqLaELOTueHo9vivE5u5qUd37Ntkiev931p4zpbwkSGWYaDo9G3ay7T%2FidFr6Rcz2thsRE%0AHwi63BHAN2031HCHGVq5CwJ8iOWb|预订|2400000Z3501|Z35|BXP|GZQ|BXP|GZQ|11:49|09:10|21:21|Y|OCwIdQuwdzRgKs2UOl5KFJDSdruSarrzz8A0Bly5EC4LVQuRcjlT18Y%2FEUUM1w9tAsfYsHAZNFk%3D|20170624|3|P4|01|05|0|0||14||4|||有||有|有|||||1040106030|14163\",\"0umlUL9gn4PuOT9YQRhYsG8QYI2O6DdspMkvQ%2FtId30hr1rwumpab2SQ2ThO6Vr%2BtkdioExNqc%2F1%0AQn2cj6ZsNfvUAhE%2B9Yn1piGoegf78py2tdAzJm9%2Bc%2BR0rUN54eIP%2BuubrOAHy7R%2Fkk9hKWCpwRwR%0Abi8A75u8%2BpRVSHBs%2ByERyMh1i00gqYGEvNtZFn3Rd6uCjqBUi8W913vi7eqqsiYchjjFwAftNEqt%0AM%2Bk9Poo%3D|预订|2400000G670B|G67|BXP|IZQ|BXP|IZQ|12:13|22:18|10:05|Y|%2FgtehVClGm%2FddSu%2FzUCcqS7xbIxJE1agIkZezyo4ePTkJzoi|20170624|3|P3|01|18|1|0|||||||||||有|有|有||O0M090|OM9\",\"7PZWgbcIx1oZbTTZX2azv7cHPHJEyqVfEuJSjjs8EvCmfAXUETaifI8wG94fer8d9pI7aoQluMP1%0A7gRrUfQesISws8Aeh4OvXxTYBmrGfWvNjHSmwbhrUpOp%2FrcvI9RRdysUoQUZsFCpWFC3ijy1PC5P%0A4lUJAGEG2a5oqoerrWwwJS3G8obFpKDJn6OxeGb7kiJfLikZXsgQRFNVyPlkNt9vWfS%2FXK4vXyW2%0AexElGjxBuuCIG0rqRFPHqLD655iNGUgxgQ%3D%3D|预订|2400000Z9703|Z97|BXP|GGQ|BXP|GGQ|12:40|10:01|21:21|Y|Gl7Pk1%2BurDgMe7ulIITV57umtPk3VGkHNsUfVNw6m3KFC7cnmTm5AB4TF%2F%2BykyzbcLNx7F5HbhE%3D|20170624|3|P3|01|05|0|0||无||14|||有||有|有|||||1040601030|14613\",\"3%2FrjvM0YruYNDHmzPIQP0oHSZ5DQ863XATlm%2BaNc5uTqEAb1BzoXzQnU7wP2SYP%2BdOl%2BY5noz7Pb%0AfEF4Aj%2FLPGjKpIKnB57Ueg%2F81e76CXSw%2Byv7SojDRIf99Nn2ddm3bTdXXihx%2FmO9%2BhJdxQaOQiHE%0AK5ETWE7%2BPd0oIiEG4ObmZRy5ufEp%2FiSHCuXQGJcyIiQWiNArf7XYhA0gU%2B1s88ypXzGGwSysGd%2F2%0AHcdmrJU%3D|预订|2400000G6907|G69|BXP|IZQ|BXP|IZQ|13:04|22:23|09:19|Y|ZxmMCZgLlSbx2%2B%2BL6Ksdt6Oy8tpr9MHDFiI8bS3WiSqWFX9H|20170624|3|P4|01|13|1|0|||||||||||有|有|有||O0M090|OM9\",\"ofKEuGcv3JcAGliYPMPFGxSx1ImQuEGfcGTpVDx%2Be6kR4ubd1wCNriDOrijVIXjBQmf073fqK8Us%0AzkVcGq5zzteuOZu3XRFmAh6mhU5elUQ%2FUvY%2B2IGf2MZ%2BtihrUF8nVNuu%2F5mnOMZMCHDmb8xvuJ2F%0AW8s5Hm%2F%2F%2Fa5iBpBGXEgH2EucO6wTIaFb%2BpDIGjB9Hz1fjUJufalHVu1hkjU8NINpOvcVIy2Ps%2FWL%0AiYDaAoyNAe1J7L6TuhI9H9gQ0TsmywhC0A%3D%3D|预订|240000Z2010F|Z201|BXP|SEQ|BXP|GZQ|17:54|15:43|21:49|Y|br30ydm0ezH7UEieG0lIxaK%2FFUbZ6Qf6QJZ%2FtXH3ba%2BuG8gKiZIuhNeIo1QKm9gsO3bfrjrWwPY%3D|20170624|3|P2|01|09|0|0||无||无|||有||有|有|||||1040106030|14163\",\"4jD5uA7SmDfIumMtBF8z7O545I4paPOU4Kpm6lhhtpZJoJzRBzYyxxYkNXcmEeNe%2BMWyU8Oyjelo%0AxEnMBjK3xdqQASK92dBrn0givlyEIY8%2FMp8DEBtJQE3PKmI4bGy5jIjuHB6LRwcuNjFL77lJFg3e%0AVixnumtPYnfSdke4yfkYHoiFrDVisAyW%2FRlcsPJd0QMkwkrrYNM3vbvLi2hikJs50e2U4D1S|预订|240000D9010F|D901|BXP|IOQ|BXP|IZQ|20:10|06:28|10:18|Y|2wnTfcCwhdyCFaISvDqtZwVkOqC6e06R|20170624|3|P2|01|04|1|0|||||||||||有|||有|F0O0|FO\",\"j9A54qH4mIVcBCh70JZe6Cv8IIG3AivckpfEfLvjd%2FD6JXRZPkmyHTLecP3uPaJG2UnXggpNBfNw%0ARyYeEG9nNuBkThJaMI2KrAcc95wZTE1uG%2FLTMYyb9cw%2BANkwXLSY2R0HgF79N3aEwK3pd6%2Fe%2FFWM%0A1TcECLo7Ci%2BMOChoHTrnk2uN%2B1SGOpsGvjB8UdCyQAXbt9wyXeGJo9L4y%2FYAR8ZOm2%2F6BqiQ|预订|240000D90305|D903|BXP|IOQ|BXP|IZQ|20:15|06:33|10:18|Y|enrzwpmLu0%2BqGKvSrvjvHB4oTTKP0yQD|20170624|3|P3|01|05|1|0|||||||||||有|||有|F0O0|FO\",\"mpzMXDPVfvjLF8iVmkdq8bSm9ErghojtZ1Su9ccRJgeIoeOHqosCPR8lHXfsAsOZPMljI%2F76CDub%0AcUsjPOlYH5Y5d4NPsX6lUbJ%2BD8%2ByvDEHi0v0tVbeGsOwvl77CrKH5poTfUE%2FMVgac14LRjDmIXdO%0Ah%2Bsr7OFdF%2FvdpcyMsa%2Ft7FG%2BVDneIHcWhufQeCWKr8sBBaY2EFHbB00V6xOlieGxPjQhjKPd|预订|240000D90900|D909|BXP|IOQ|BXP|IZQ|20:20|06:38|10:18|Y|MTF%2FdCP8tzp57Os8zfINuWgTfjVik6Uw|20170624|3|P4|01|05|1|0|||||||||||有|||有|O0F0|OF\",\"bRyThTrSqZ6ZVpS8OlFCE3XA22dAUye9s%2F%2Fz7hLua0v%2BVsiJU0YzUVaxX8MPSncI2V6aNIkbTQKN%0AFOWoeyyaGPBkDaEJ%2BA%2Biv0Pydh8AN2byR2WIbWfkj2Wc4GBLP5HoBipDLW4JEd3lon5GQLxpHVwb%0A0l%2FeyzK0w3PGcWTXeFn3368bOzShF9sNNOsNkKOoZZElgDOwru626SshEYVUGpVRQAeBd6NS|预订|240000D92707|D927|BXP|IOQ|BXP|IZQ|20:25|06:48|10:23|Y|HEYTdRDA12hrZP%2FKjhEfYwYeiXg%2B9yJ8|20170624|3|P3|01|05|1|0|||||||||||有|||有|F0O0|FO\",\"|预订|240000D9230K|D923|BXP|ZHQ|BXP|IZQ|20:35|06:43|10:08|N|ZkrvPYZVinQIsZAISXwLRMtzgSpN9S33|20170624|3|P3|01|05|1|0|||||||||||无|||无|O0F0|OF\"],\"flag\":\"1\",\"map\":{\"GGQ\":\"广州东\",\"BXP\":\"北京西\",\"IZQ\":\"广州南\",\"GZQ\":\"广州\"}},\"messages\":[],\"validateMessages\":{}}";
        String result = new SearchResultChangeMethod().changeSearchResult(str);
        System.out.println(result);
    }

}
