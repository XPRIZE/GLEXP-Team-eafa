package com.motoli.apps.allsubjects;

/**
 * Part of Project Motoli All Subjects
 * for Education Technology For Development
 * created by Aaron D Michaelis Borsay
 * on 8/3/2016.
 */import android.content.res.AssetManager;
import android.media.SoundPool;
import com.android.volley.DefaultRetryPolicy;
import java.util.HashMap;
import java.util.Random;
import org.apache.commons.collections4.map.LinkedMap;

public class Model {
    private static Model instance;
    int[] completedSets;
    private Directions directions;
    private float insideThreshold;
    private Language language;
    private int letterIndex;
    private int letterIndexOld;
    private Level level;
    int[] objectIndex;
    int objectsNum;
    private Order order;
    private float outsideThreshold;
    Random randomGenerator;
    public SoundPool sounds;
    private int stars;
    private LinkedMap<String, Symbol> symbolProperties;
    private HashMap<SymbolType, Boolean> symbolSets;

    /* renamed from: net.homeip.alphabet.Model.1 */
    static /* synthetic */ class C06731 {
        static final /* synthetic */ int[] $SwitchMap$net$homeip$alphabet$Model$Level;

        static {
            $SwitchMap$net$homeip$alphabet$Model$Level = new int[Level.values().length];
            try {
                $SwitchMap$net$homeip$alphabet$Model$Level[Level.easy.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$homeip$alphabet$Model$Level[Level.medium.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$homeip$alphabet$Model$Level[Level.hard.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public enum Directions {
        yes,
        no
    }

    public enum Language {
        en,
        it,
        es,
        fr,
        de
    }

    public enum Level {
        easy,
        medium,
        hard
    }

    public enum Order {
        random,
        sequence
    }

    public class Symbol {
        float aspectRatio;
        float maxCoverage;
        private String sound;
        String symbol;
        SymbolType type;

        public String getSound() {
            return this.sound;
        }

        Symbol(String symbol, String sound, SymbolType type, float aspectRatio, float maxCoverage) {
            this.symbol = symbol;
            this.sound = sound;
            this.type = type;
            this.aspectRatio = aspectRatio;
            this.maxCoverage = maxCoverage;
        }
    }

    public enum SymbolType {
        number,
        uppercase,
        lowercase,
        cursive
    }

    static {
        instance = null;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setSymbolsSet(SymbolType st, boolean enabled) {
        this.symbolSets.put(st, Boolean.valueOf(enabled));
    }

    public boolean isSymbolSetEnabled(SymbolType st) {
        return ((Boolean) this.symbolSets.get(st)).booleanValue();
    }

    private void initializeSymbols() {
        this.symbolProperties.put("au", new Symbol("au", "a", SymbolType.uppercase, 0.75f, 0.694612f));
        this.symbolProperties.put("bu", new Symbol("bu", "b", SymbolType.uppercase, 0.75f, 0.6815266f));
        this.symbolProperties.put("cu", new Symbol("cu", "c", SymbolType.uppercase, 0.75f, 0.69382167f));
        this.symbolProperties.put("du", new Symbol("du", "d", SymbolType.uppercase, 0.75f, 0.6799772f));
        this.symbolProperties.put("eu", new Symbol("eu", "e", SymbolType.uppercase, 0.75f, 0.64994335f));
        this.symbolProperties.put("fu", new Symbol("fu", "f", SymbolType.uppercase, 0.75f, 0.6034738f));
        this.symbolProperties.put("gu", new Symbol("gu", "g", SymbolType.uppercase, 0.75f, 0.65180206f));
        this.symbolProperties.put("hu", new Symbol("hu", "h", SymbolType.uppercase, 0.75f, 0.6565397f));
        this.symbolProperties.put("iu", new Symbol("iu", "i", SymbolType.uppercase, 0.75f, 0.5066235f));
        this.symbolProperties.put("ju", new Symbol("ju", "j", SymbolType.uppercase, 0.75f, 0.6473891f));
        this.symbolProperties.put("ku", new Symbol("ku", "k", SymbolType.uppercase, 0.75f, 0.66019195f));
        this.symbolProperties.put("lu", new Symbol("lu", "l", SymbolType.uppercase, 0.75f, 0.5918169f));
        this.symbolProperties.put("mu", new Symbol("mu", "m", SymbolType.uppercase, 0.75f, 0.737191f));
        this.symbolProperties.put("nu", new Symbol("nu", "n", SymbolType.uppercase, 0.75f, 0.690216f));
        this.symbolProperties.put("ou", new Symbol("ou", "o", SymbolType.uppercase, 0.75f, 0.6589779f));
        this.symbolProperties.put("pu", new Symbol("pu", "p", SymbolType.uppercase, 0.75f, 0.62472767f));
        this.symbolProperties.put("qu", new Symbol("qu", "q", SymbolType.uppercase, 0.75f, 0.700843f));
        this.symbolProperties.put("ru", new Symbol("ru", "r", SymbolType.uppercase, 0.75f, 0.67363536f));
        this.symbolProperties.put("su", new Symbol("su", "s", SymbolType.uppercase, 0.75f, 0.6868342f));
        this.symbolProperties.put("tu", new Symbol("tu", "t", SymbolType.uppercase, 0.75f, 0.6526856f));
        this.symbolProperties.put("uu", new Symbol("uu", "u", SymbolType.uppercase, 0.75f, 0.6359763f));
        this.symbolProperties.put("vu", new Symbol("vu", "v", SymbolType.uppercase, 0.75f, 0.5545523f));
        this.symbolProperties.put("wu", new Symbol("wu", "w", SymbolType.uppercase, 0.75f, 0.5387339f));
        this.symbolProperties.put("xu", new Symbol("xu", "x", SymbolType.uppercase, 0.75f, 0.7091258f));
        this.symbolProperties.put("yu", new Symbol("yu", "y", SymbolType.uppercase, 0.75f, 0.7357963f));
        this.symbolProperties.put("zu", new Symbol("zu", "z", SymbolType.uppercase, 0.75f, 0.6359338f));
        this.symbolProperties.put("al", new Symbol("al", "a", SymbolType.lowercase, 0.75f, 0.53716743f));
        this.symbolProperties.put("bl", new Symbol("bl", "b", SymbolType.lowercase, 0.75f, 0.50782657f));
        this.symbolProperties.put("cl", new Symbol("cl", "c", SymbolType.lowercase, 0.75f, 0.6684981f));
        this.symbolProperties.put("dl", new Symbol("dl", "d", SymbolType.lowercase, 0.75f, 0.5150939f));
        this.symbolProperties.put("el", new Symbol("el", "e", SymbolType.lowercase, 0.75f, 0.54417247f));
        this.symbolProperties.put("fl", new Symbol("fl", "f", SymbolType.lowercase, 0.75f, 0.51373106f));
        this.symbolProperties.put("gl", new Symbol("gl", "g", SymbolType.lowercase, 0.75f, 0.5707958f));
        this.symbolProperties.put("hl", new Symbol("hl", "h", SymbolType.lowercase, 0.75f, 0.52920556f));
        this.symbolProperties.put("il", new Symbol("il", "i", SymbolType.lowercase, 0.75f, 0.4090989f));
        this.symbolProperties.put("jl", new Symbol("jl", "j", SymbolType.lowercase, 0.75f, 0.47438565f));
        this.symbolProperties.put("kl", new Symbol("kl", "k", SymbolType.lowercase, 0.75f, 0.5314346f));
        this.symbolProperties.put("ll", new Symbol("ll", "l", SymbolType.lowercase, 0.75f, 0.5823687f));
        this.symbolProperties.put("ml", new Symbol("ml", "m", SymbolType.lowercase, 0.75f, 0.5715445f));
        this.symbolProperties.put("nl", new Symbol("nl", "n", SymbolType.lowercase, 0.75f, 0.46455944f));
        this.symbolProperties.put("ol", new Symbol("ol", "o", SymbolType.lowercase, 0.75f, 0.5594904f));
        this.symbolProperties.put("pl", new Symbol("pl", "p", SymbolType.lowercase, 0.75f, 0.560747f));
        this.symbolProperties.put("ql", new Symbol("ql", "q", SymbolType.lowercase, 0.75f, 0.52190065f));
        this.symbolProperties.put("rl", new Symbol("rl", "r", SymbolType.lowercase, 0.75f, 0.55049413f));
        this.symbolProperties.put("sl", new Symbol("sl", "s", SymbolType.lowercase, 0.75f, 0.54816425f));
        this.symbolProperties.put("tl", new Symbol("tl", "t", SymbolType.lowercase, 0.75f, 0.5068942f));
        this.symbolProperties.put("ul", new Symbol("ul", "u", SymbolType.lowercase, 0.75f, 0.58497363f));
        this.symbolProperties.put("vl", new Symbol("vl", "v", SymbolType.lowercase, 0.75f, 0.55162275f));
        this.symbolProperties.put("wl", new Symbol("wl", "w", SymbolType.lowercase, 0.75f, 0.5266999f));
        this.symbolProperties.put("xl", new Symbol("xl", "x", SymbolType.lowercase, 0.75f, 0.5664745f));
        this.symbolProperties.put("yl", new Symbol("yl", "y", SymbolType.lowercase, 0.75f, 0.5632334f));
        this.symbolProperties.put("zl", new Symbol("zl", "z", SymbolType.lowercase, 0.75f, 0.6144502f));

        this.symbolProperties.put("0n", new Symbol("0n", "0", SymbolType.number, 0.75f, 0.63747454f));
        this.symbolProperties.put("1n", new Symbol("1n", "1", SymbolType.number, 0.75f, 0.60102385f));
        this.symbolProperties.put("2n", new Symbol("2n", "2", SymbolType.number, 0.75f, 0.6654361f));
        this.symbolProperties.put("3n", new Symbol("3n", "3", SymbolType.number, 0.75f, 0.67857707f));
        this.symbolProperties.put("4n", new Symbol("4n", "4", SymbolType.number, 0.75f, 0.6708424f));
        this.symbolProperties.put("5n", new Symbol("5n", "5", SymbolType.number, 0.75f, 0.66063136f));
        this.symbolProperties.put("6n", new Symbol("6n", "6", SymbolType.number, 0.75f, 0.66233164f));
        this.symbolProperties.put("7n", new Symbol("7n", "7", SymbolType.number, 0.75f, 0.64893645f));
        this.symbolProperties.put("8n", new Symbol("8n", "8", SymbolType.number, 0.75f, 0.71695167f));
        this.symbolProperties.put("9n", new Symbol("9n", "9", SymbolType.number, 0.75f, 0.6157804f));
        /*
        this.symbolProperties.put("shape_circle", new Symbol("shape_circle", ".circle", SymbolType.number, 0.97f, 0.41980252f));
        this.symbolProperties.put("shape_oval", new Symbol("shape_oval", ".oval", SymbolType.number, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 0.39868125f));
        this.symbolProperties.put("shape_triangle", new Symbol("shape_triangle", ".triangle", SymbolType.number, 1.14f, 0.3697436f));
        this.symbolProperties.put("shape_square", new Symbol("shape_square", ".square", SymbolType.number, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 0.360691f));
        this.symbolProperties.put("shape_rectangle", new Symbol("shape_rectangle", ".rectangle", SymbolType.number, 0.6f, 0.33339518f));
        this.symbolProperties.put("shape_pentagon", new Symbol("shape_pentagon", ".pentagon", SymbolType.number, 1.05f, 0.4083445f));
        this.symbolProperties.put("shape_hexagon", new Symbol("shape_hexagon", ".hexagon", SymbolType.number, 1.15f, 0.46336895f));
        this.symbolProperties.put("shape_octagon", new Symbol("shape_octagon", ".octagon", SymbolType.number, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 0.39894933f));
        this.symbolProperties.put("shape_star", new Symbol("shape_star", ".star", SymbolType.number, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 0.5121054f));
        this.symbolProperties.put("shape_trapeze", new Symbol("shape_trapeze", ".trapeze", SymbolType.number, 0.69f, 0.39561188f));
        */
    }

    public Symbol getSymbol() {
        if (this.order == Order.random) {
            do {
                this.letterIndex = this.randomGenerator.nextInt(this.symbolProperties.size());
            } while (!((Boolean) this.symbolSets.get(((Symbol) this.symbolProperties.getValue(this.letterIndex)).type)).booleanValue());
        } else {
            do {
                this.letterIndex = this.objectIndex[this.language.ordinal()];
                this.objectIndex[this.language.ordinal()] = (this.objectIndex[this.language.ordinal()] + 1) % this.symbolProperties.size();
            } while (!((Boolean) this.symbolSets.get(((Symbol) this.symbolProperties.getValue(this.letterIndex)).type)).booleanValue());
        }
        return (Symbol) this.symbolProperties.getValue(this.letterIndex);
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public float getInsideThreshold() {
        return this.insideThreshold;
    }

    public float getOutsideThreshold() {
        return this.outsideThreshold;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
        /*
        switch (C06731.$SwitchMap$net$homeip$alphabet$Model$Level[level.ordinal()]) {
            case MainActivity.OPTIONS_FRAGMENT :
                this.outsideThreshold = 0.3f;
                this.insideThreshold = 0.55f;
            case MainActivity.TROPHIES_FRAGMENT :
                this.outsideThreshold = 0.2f;
                this.insideThreshold = 0.6f;
            case MainActivity.NUM_STARS :
                this.outsideThreshold = 0.1f;
                this.insideThreshold = 0.8f;
            default:
        }
    */
    }

    public Model(AssetManager assetManager) {
        this.randomGenerator = new Random();
        this.letterIndex = 0;
        this.letterIndexOld = 0;
        this.language = Language.en;
        this.level = Level.easy;
        this.order = Order.random;
        this.directions = Directions.yes;
        this.completedSets = new int[]{0, 0, 0, 0, 0};
        this.objectIndex = new int[]{0, 0, 0, 0, 0};
        this.objectsNum = 0;
        this.symbolProperties = new LinkedMap();
        this.outsideThreshold = 0.1f;
        this.insideThreshold = 0.6f;
        this.symbolSets = new HashMap();
        this.sounds = new SoundPool(10, 3, 0);
        this.stars = 0;
        initializeSymbols();
    }

    public Directions getDirections() {
        return this.directions;
    }

    public void setDirections(Directions directions) {
        this.directions = directions;
    }

    public int getStars() {
        return this.stars;
    }

    public void clearStars() {
        this.stars = 0;
    }

    public void addStar() {
        this.stars = (this.stars + 1) % 3;
    }

    public int getCompletedSets(int lang) {
        return this.completedSets[lang];
    }

    public int getCompletedSets() {
        return this.completedSets[this.language.ordinal()];
    }

    public void setCompletedSets(int lang, int completedSets) {
        this.completedSets[lang] = completedSets;
    }

    public void resetProgress() {
        int i;
        for (i = 0; i < this.completedSets.length; i++) {
            this.completedSets[i] = 0;
        }
        for (i = 0; i < this.objectIndex.length; i++) {
            this.objectIndex[i] = 0;
        }
    }

    public void setCompletedSets(int completedSets) {
        this.completedSets[this.language.ordinal()] = completedSets;
    }

    public void increaseCompletedSets() {
        int[] iArr = this.completedSets;
        int ordinal = this.language.ordinal();
        iArr[ordinal] = iArr[ordinal] + 1;
    }

    public int getObjectIndex(int lang) {
        return this.objectIndex[lang];
    }

    public int getObjectIndex() {
        return this.objectIndex[this.language.ordinal()];
    }

    public void setObjectIndex(int lang, int objectIndex) {
        this.objectIndex[lang] = objectIndex;
    }

    public void setObjectIndex(int objectIndex) {
        this.objectIndex[this.language.ordinal()] = objectIndex;
    }
}
