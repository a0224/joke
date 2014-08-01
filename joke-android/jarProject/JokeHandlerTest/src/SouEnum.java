public enum SouEnum
{
    // 鍏充簬鎺ㄥ箍浣�
    SOU_1(1, "鍐呮兜娈靛瓙"), SOU_2(2, "绯椾簨鐧剧"), SOU_3(3, "鐧惧害鏂伴椈"), SOU_4(4, "鑵捐鏂伴椈"), SOU_5(5, "缃戞槗鏂伴椈"), SOU_6(6, "360鏂伴椈");

    private final Integer id;

    private final String name;

    SouEnum(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    public static void main(String[] args)
    {
        for (SouEnum s : SouEnum.values())
            System.out.println(s.getId() + ", SourceEnum " + s.getName());
        System.out.println(SouEnum.SOU_1.getId());
    }

}
