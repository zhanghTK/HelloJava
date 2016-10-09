package tk.zhangh.java.effective;

/**
 * 营养成分标签类
 * servingSize，serving必选,其余字段可选
 * 使用构建器模拟创建过程
 * Created by ZhangHao on 2016/10/9.
 */
public class NutritionFacts {
    private final int servingSize;
    private final int serving;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    private NutritionFacts(Builder builder) {
        servingSize = builder.servingSize;
        serving = builder.serving;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public static class Builder implements tk.zhangh.java.effective.Builder<NutritionFacts> {
        private final int servingSize;
        private final int serving;
        private int calories;
        private int fat;
        private int sodium;
        private int carbohydrate;

        public Builder(int servingSize, int serving) {
            this.servingSize = servingSize;
            this.serving = serving;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder fat(int fat) {
            this.fat = fat;
            return this;
        }

        public Builder sodium(int sodium) {
            this.sodium = sodium;
            return this;
        }

        public Builder cardbohydrate(int cardbohydrate) {
            this.carbohydrate = cardbohydrate;
            return this;
        }

        @Override
        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    @Override
    public String toString() {
        return "NutritionFacts{" +
                "servingSize=" + servingSize +
                ", serving=" + serving +
                ", calories=" + calories +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", carbohydrate=" + carbohydrate +
                '}';
    }

    public static void main(String[] args) {
        NutritionFacts nutritionFacts = new Builder(240, 8).calories(100).sodium(35).cardbohydrate(27).build();
        System.out.println(nutritionFacts.toString());
    }
}
