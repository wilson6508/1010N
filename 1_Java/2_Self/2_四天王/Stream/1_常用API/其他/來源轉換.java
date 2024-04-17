import com.google.gson.Gson;
import java.util.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Ex7 {

    public static void main(String[] args) {
        Ex7 ex7 = new Ex7();
        String str = "bbs;bbs_reply;forum;facebook;facebook_reply;instagram;instagram_reply;youtube;youtube_reply;social;location;comment_app;podcast;qa;blog;news;news_reply";
        System.out.println(ex7.transferSearchSource(str));
    }

    public String transferSearchSource(String searchSource) {

        String[] searchSourceArray = searchSource.split(";");

        Set<String> forumGroup = new LinkedHashSet<>();
        Set<String> socialGroup = new LinkedHashSet<>();
        Set<String> commentGroup = new LinkedHashSet<>();
        Set<String> qaGroup = new LinkedHashSet<>();
        Set<String> blogGroup = new LinkedHashSet<>();
        Set<String> newsGroup = new LinkedHashSet<>();

//        {
//            Map<String, String> sourceMap = new LinkedHashMap<>();
//            sourceMap.put("bbs", "PTT主文");
//            sourceMap.put("bbs_reply", "PTT回文");
//            sourceMap.put("forum", "其他討論區");
//            sourceMap.put("facebook", "facebook主文");
//            sourceMap.put("facebook_reply", "facebook回文");
//            sourceMap.put("instagram", "Instagram主文");
//            sourceMap.put("instagram_reply", "Instagram回文");
//            sourceMap.put("youtube", "YouTube主文");
//            sourceMap.put("youtube_reply", "YouTube回文");
//            sourceMap.put("social", "其他社群網站");
//            sourceMap.put("location", "地點評論");
//            sourceMap.put("comment_app", "APP評論");
//            sourceMap.put("podcast", "Podcast評論");
//            sourceMap.put("qa", "問答網站");
//            sourceMap.put("blog", "部落格");
//            sourceMap.put("news", "新聞");
//            sourceMap.put("news_reply", "新聞回文");
//        }

        for (String str : searchSourceArray) {
            switch (str) {
                // 討論區 //
                case "bbs":
                    forumGroup.add("PTT主文");
                    break;
                case "bbs_reply":
                    forumGroup.add("PTT回文");
                    break;
                case "forum":
                    forumGroup.add("其他討論區");
                    break;
                // 社群網站 //
                case "facebook":
                    socialGroup.add("facebook主文");
                    break;
                case "facebook_reply":
                    socialGroup.add("facebook回文");
                    break;
                case "instagram":
                    socialGroup.add("Instagram主文");
                    break;
                case "instagram_reply":
                    socialGroup.add("Instagram回文");
                    break;
                case "youtube":
                    socialGroup.add("YouTube主文");
                    break;
                case "youtube_reply":
                    socialGroup.add("YouTube回文");
                    break;
                case "social":
                    socialGroup.add("其他社群網站");
                    break;
                // 評論 //
                case "location":
                    commentGroup.add("地點評論");
                    break;
                case "comment_app":
                    commentGroup.add("APP評論");
                    break;
                case "podcast":
                    commentGroup.add("Podcast評論");
                    break;
                // 問答網站 //
                case "qa":
                    qaGroup.add("問答網站");
                    break;
                // 部落格 //
                case "blog":
                    blogGroup.add("部落格");
                    break;
                // 新聞 //
                case "news":
                    newsGroup.add("新聞");
                    break;
                case "news_reply":
                    newsGroup.add("新聞回文");
                    break;
            }
        }

        Map<String, Set> sourceCategory = new LinkedHashMap<>();
        sourceCategory.put("討論區", forumGroup);
        sourceCategory.put("社群網站", socialGroup);
        sourceCategory.put("評論", commentGroup);
        sourceCategory.put("問答網站", qaGroup);
        sourceCategory.put("部落格", blogGroup);
        sourceCategory.put("新聞", newsGroup);

//        方法1
        Map<String, Set> test = sourceCategory.entrySet().stream()
                .filter(e -> e.getValue().size() != 0)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));


//        方法2
//        Map<String, Set> test = new LinkedHashMap<>();
//        sourceCategory.entrySet().stream()
//                                 .filter(e -> e.getValue().size() != 0)
//                                 .forEach(e -> test.put(e.getKey(), e.getValue()));


//        方法3        keySet() values()
//        Map<String, Set> test = new LinkedHashMap<>();
//        sourceCategory.keySet().stream()
//                .filter(key -> sourceCategory.get(key).size() != 0)
//                .forEach(key -> test.put(key, sourceCategory.get(key)));


//        方法4
//        Iterator iterator = sourceCategory.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry pair = (Map.Entry)iterator.next();
//            if (pair.getValue().equals(zero)) {
//                iterator.remove(); // avoids a ConcurrentModificationException
//            }
//        }
//        return new Gson().toJson(sourceCategory);

        return new Gson().toJson(test);
    }


}
