public static void main(String[] args) {
    int count = Runtime.getRuntime().availableProcessors();
    System.out.println(count);
    // 4核心8(線程)邏輯處理器 count=8
}