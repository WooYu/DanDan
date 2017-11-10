package com.dhxgzs.goodluck.entiey;

/**
 * Created by 宁 on 2017/6/29.
 */

public class PeiLvTeShuShuoMingBean {

	  /**
     * state : success
     * biz_content : {"info0":"大小单双总注小于等于100时，如果遇到开13、14，大小单双赔率1.5倍,组合赔率1.5倍","info1":"大小单双总注大于100时，如果遇到开13、14，大小单双赔率1倍,组合赔率1倍"}
     */

    private String state;
    private BizContentBean biz_content;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BizContentBean getBiz_content() {
        return biz_content;
    }

    public void setBiz_content(BizContentBean biz_content) {
        this.biz_content = biz_content;
    }

    public static class BizContentBean {
    	/**
         * info0 : 大小单双总注小于等于100时，如果遇到开13、14，大小单双赔率1.5倍,组合赔率1.5倍
         * info1 : 大小单双总注大于100时，如果遇到开13、14，大小单双赔率1倍,组合赔率1倍
         */

        private String info0;
        private String info1;

        public String getInfo0() {
            return info0;
        }

        public void setInfo0(String info0) {
            this.info0 = info0;
        }

        public String getInfo1() {
            return info1;
        }

        public void setInfo1(String info1) {
            this.info1 = info1;
        }
    }
}
