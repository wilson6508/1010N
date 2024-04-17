public enum ClassifySource {
	bbs("PTT主文", "討論區"),
	bbs_reply("PTT回文", "討論區"),
	forum("其他討論區", "討論區"),
	facebook("facebook主文", "社群網站"),
	facebook_reply("facebook回文", "社群網站"),
	instagram("Instagram主文", "社群網站"),
	instagram_reply("Instagram回文", "社群網站"),
	youtube("YouTube主文", "社群網站"),
	youtube_reply("YouTube回文", "社群網站"),
	social("其他社群網站", "社群網站"),
	location("地點評論", "評論"),
	comment_app("APP評論", "評論"),
	podcast("Podcast評論", "評論"),
	qa("問答網站", "問答網站"),
	blog("部落格", "部落格"),
	news("新聞", "新聞"),
	news_reply("新聞回文", "新聞"),;

	private String chineseName;
	private String group;

	ClassifySource(String chineseName, String source) {
		this.chineseName = chineseName;
		this.group = source;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}

public String transferSearchSource(String searchSource) {
	String[] searchSourceArray = searchSource.split(";");
	Map<String, Set> sourceCategory = new LinkedHashMap<>();
	for (String str : searchSourceArray) {
		ClassifySource classifySource = ClassifySource.valueOf(str);
		sourceCategory.put(classifySource.getGroup(), new LinkedHashSet<>());
	}
	for (String str : searchSourceArray) {
		ClassifySource classifySource = ClassifySource.valueOf(str);
		sourceCategory.get(classifySource.getGroup()).add(classifySource.getChineseName());
	}
	return new Gson().toJson(sourceCategory);
}
------------------------------------------------------------------------------
public static void main(String[] args) {
	System.out.println(ModuleType.CREATE_DATA.getModule());
	System.out.println(ModuleType.UPDATE_DATA.getModulePath());
	System.out.println(ModuleType.DELETE_DATA.getAuthorization());
	for (ModuleType moduleType : ModuleType.values()) {
		System.out.println(moduleType.getModule());
	}
    // ModuleType moduleType = ModuleType.valueOf(modelName.toUpperCase());
}

public enum ModuleType {
	CREATE_DATA("create_data", "/instantChannel/readInfo", "IS_DATA_SOURCE_INFO_VIEW"),
	UPDATE_DATA("update_data", "/instantChannel/updateInfo", "IS_DATA_SOURCE_INFO_EDIT"),
	DELETE_DATA("delete_data", "/instantChannel/deleteInfo", "IS_DATA_SOURCE_INFO_EDIT");
	private String module;
	private String modulePath;
	private String authorization;
	
	ModuleType(String module, String modulePath, String authorization) {
		this.module = module;
		this.modulePath = modulePath;
		this.authorization = authorization;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getModulePath() {
		return modulePath;
	}

	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
}