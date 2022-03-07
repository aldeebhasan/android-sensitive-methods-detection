package java.awt.font;

import java.text.*;
import java.io.*;
import java.util.*;

public final class TextAttribute extends AttributedCharacterIterator.Attribute
{
    private static final Map<String, TextAttribute> instanceMap;
    static final long serialVersionUID = 7744112784117861702L;
    public static final TextAttribute FAMILY;
    public static final TextAttribute WEIGHT;
    public static final Float WEIGHT_EXTRA_LIGHT;
    public static final Float WEIGHT_LIGHT;
    public static final Float WEIGHT_DEMILIGHT;
    public static final Float WEIGHT_REGULAR;
    public static final Float WEIGHT_SEMIBOLD;
    public static final Float WEIGHT_MEDIUM;
    public static final Float WEIGHT_DEMIBOLD;
    public static final Float WEIGHT_BOLD;
    public static final Float WEIGHT_HEAVY;
    public static final Float WEIGHT_EXTRABOLD;
    public static final Float WEIGHT_ULTRABOLD;
    public static final TextAttribute WIDTH;
    public static final Float WIDTH_CONDENSED;
    public static final Float WIDTH_SEMI_CONDENSED;
    public static final Float WIDTH_REGULAR;
    public static final Float WIDTH_SEMI_EXTENDED;
    public static final Float WIDTH_EXTENDED;
    public static final TextAttribute POSTURE;
    public static final Float POSTURE_REGULAR;
    public static final Float POSTURE_OBLIQUE;
    public static final TextAttribute SIZE;
    public static final TextAttribute TRANSFORM;
    public static final TextAttribute SUPERSCRIPT;
    public static final Integer SUPERSCRIPT_SUPER;
    public static final Integer SUPERSCRIPT_SUB;
    public static final TextAttribute FONT;
    public static final TextAttribute CHAR_REPLACEMENT;
    public static final TextAttribute FOREGROUND;
    public static final TextAttribute BACKGROUND;
    public static final TextAttribute UNDERLINE;
    public static final Integer UNDERLINE_ON;
    public static final TextAttribute STRIKETHROUGH;
    public static final Boolean STRIKETHROUGH_ON;
    public static final TextAttribute RUN_DIRECTION;
    public static final Boolean RUN_DIRECTION_LTR;
    public static final Boolean RUN_DIRECTION_RTL;
    public static final TextAttribute BIDI_EMBEDDING;
    public static final TextAttribute JUSTIFICATION;
    public static final Float JUSTIFICATION_FULL;
    public static final Float JUSTIFICATION_NONE;
    public static final TextAttribute INPUT_METHOD_HIGHLIGHT;
    public static final TextAttribute INPUT_METHOD_UNDERLINE;
    public static final Integer UNDERLINE_LOW_ONE_PIXEL;
    public static final Integer UNDERLINE_LOW_TWO_PIXEL;
    public static final Integer UNDERLINE_LOW_DOTTED;
    public static final Integer UNDERLINE_LOW_GRAY;
    public static final Integer UNDERLINE_LOW_DASHED;
    public static final TextAttribute SWAP_COLORS;
    public static final Boolean SWAP_COLORS_ON;
    public static final TextAttribute NUMERIC_SHAPING;
    public static final TextAttribute KERNING;
    public static final Integer KERNING_ON;
    public static final TextAttribute LIGATURES;
    public static final Integer LIGATURES_ON;
    public static final TextAttribute TRACKING;
    public static final Float TRACKING_TIGHT;
    public static final Float TRACKING_LOOSE;
    
    protected TextAttribute(final String s) {
        super(s);
        if (this.getClass() == TextAttribute.class) {
            TextAttribute.instanceMap.put(s, this);
        }
    }
    
    @Override
    protected Object readResolve() throws InvalidObjectException {
        if (this.getClass() != TextAttribute.class) {
            throw new InvalidObjectException("subclass didn't correctly implement readResolve");
        }
        final TextAttribute textAttribute = TextAttribute.instanceMap.get(this.getName());
        if (textAttribute != null) {
            return textAttribute;
        }
        throw new InvalidObjectException("unknown attribute name");
    }
    
    static {
        instanceMap = new HashMap<String, TextAttribute>(29);
        FAMILY = new TextAttribute("family");
        WEIGHT = new TextAttribute("weight");
        WEIGHT_EXTRA_LIGHT = 0.5f;
        WEIGHT_LIGHT = 0.75f;
        WEIGHT_DEMILIGHT = 0.875f;
        WEIGHT_REGULAR = 1.0f;
        WEIGHT_SEMIBOLD = 1.25f;
        WEIGHT_MEDIUM = 1.5f;
        WEIGHT_DEMIBOLD = 1.75f;
        WEIGHT_BOLD = 2.0f;
        WEIGHT_HEAVY = 2.25f;
        WEIGHT_EXTRABOLD = 2.5f;
        WEIGHT_ULTRABOLD = 2.75f;
        WIDTH = new TextAttribute("width");
        WIDTH_CONDENSED = 0.75f;
        WIDTH_SEMI_CONDENSED = 0.875f;
        WIDTH_REGULAR = 1.0f;
        WIDTH_SEMI_EXTENDED = 1.25f;
        WIDTH_EXTENDED = 1.5f;
        POSTURE = new TextAttribute("posture");
        POSTURE_REGULAR = 0.0f;
        POSTURE_OBLIQUE = 0.2f;
        SIZE = new TextAttribute("size");
        TRANSFORM = new TextAttribute("transform");
        SUPERSCRIPT = new TextAttribute("superscript");
        SUPERSCRIPT_SUPER = 1;
        SUPERSCRIPT_SUB = -1;
        FONT = new TextAttribute("font");
        CHAR_REPLACEMENT = new TextAttribute("char_replacement");
        FOREGROUND = new TextAttribute("foreground");
        BACKGROUND = new TextAttribute("background");
        UNDERLINE = new TextAttribute("underline");
        UNDERLINE_ON = 0;
        STRIKETHROUGH = new TextAttribute("strikethrough");
        STRIKETHROUGH_ON = Boolean.TRUE;
        RUN_DIRECTION = new TextAttribute("run_direction");
        RUN_DIRECTION_LTR = Boolean.FALSE;
        RUN_DIRECTION_RTL = Boolean.TRUE;
        BIDI_EMBEDDING = new TextAttribute("bidi_embedding");
        JUSTIFICATION = new TextAttribute("justification");
        JUSTIFICATION_FULL = 1.0f;
        JUSTIFICATION_NONE = 0.0f;
        INPUT_METHOD_HIGHLIGHT = new TextAttribute("input method highlight");
        INPUT_METHOD_UNDERLINE = new TextAttribute("input method underline");
        UNDERLINE_LOW_ONE_PIXEL = 1;
        UNDERLINE_LOW_TWO_PIXEL = 2;
        UNDERLINE_LOW_DOTTED = 3;
        UNDERLINE_LOW_GRAY = 4;
        UNDERLINE_LOW_DASHED = 5;
        SWAP_COLORS = new TextAttribute("swap_colors");
        SWAP_COLORS_ON = Boolean.TRUE;
        NUMERIC_SHAPING = new TextAttribute("numeric_shaping");
        KERNING = new TextAttribute("kerning");
        KERNING_ON = 1;
        LIGATURES = new TextAttribute("ligatures");
        LIGATURES_ON = 1;
        TRACKING = new TextAttribute("tracking");
        TRACKING_TIGHT = -0.04f;
        TRACKING_LOOSE = 0.04f;
    }
}
