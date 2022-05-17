package org.mmapp.assignments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import org.mmapp.R;
import org.mmapp.util.BinaryNode;
import org.mmapp.util.BinaryTree;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.BinaryOperator;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>WildbienenActivity</b><br>
 * A quiz using a decision tree to identify various types of wild bees.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class WildbienenActivity extends AppCompatActivity {

    private BinaryTree<String> beeCheckList;
    private BinaryNode<String> currentNode;
    private TextView displayText;
    private RelativeLayout yesNoBtnGroup;
    private RelativeLayout restartBtnGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = createLinearLayout();
        displayText = createTextView("Text");
        ll.addView(displayText);

        yesNoBtnGroup = createYesNoButtons();
        yesNoBtnGroup.setVisibility(View.GONE);
        ll.addView(yesNoBtnGroup);
        restartBtnGroup = createRestartButton();
        restartBtnGroup.setVisibility(View.VISIBLE);
        ll.addView(restartBtnGroup);

        RelativeLayout imageContainer =
                createImageContainer(getResources().getDrawable(R.drawable.bee_intro));
        ll.addView(imageContainer);
        setContentView(ll);

        createDecisionTree();
        displayText.setText(getResources().getString(R.string.wildbienen_intro));

        configureActionBar();
    }

    private void configureActionBar() {
        ActionBar actionBar = getSupportActionBar();
        String    className = getClass().getSimpleName();
        actionBar.setTitle(className.substring(0, className.lastIndexOf("Activity")));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            startActivity(new Intent(WildbienenActivity.this, AssignmentsOverviewActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private LinearLayout createLinearLayout() {
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(getResources().getInteger(R.integer.white_bg));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10,10,10,10);
        return ll;
    }
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                            ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.START);
        textView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        textView.setLines(4);
        textView.setTextSize(20.0f);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(R.color.Green));
        return textView;
    }
    private RelativeLayout createRelativeLayout() {
        RelativeLayout rl = new RelativeLayout(this);
        rl.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        rl.setBackgroundColor(getResources().getInteger(R.integer.white_bg));
        rl.setVerticalGravity(RelativeLayout.ALIGN_PARENT_TOP);
        rl.setHorizontalGravity(RelativeLayout.CENTER_HORIZONTAL);
        rl.setPadding(50,0,50,0);
        return rl;
    }
    private Button createButton(String text, String leftOrRight) {
        Button btn = new Button(this);
        RelativeLayout.LayoutParams btnLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                  ViewGroup.LayoutParams.WRAP_CONTENT);
        btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        if (leftOrRight.equals("left")) {
            btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (leftOrRight.equals("right")) {
            btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            // Restart
            btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            btnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        btn.setLayoutParams(btnLayoutParams);
        btn.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        btn.setTextSize(20.0f);
        btn.setText(text);
        btn.setTransformationMethod(null);
        btn.setPadding(0,0,0,0);
        return btn;
    }
    private RelativeLayout createImageContainer(Drawable drawable) {
        RelativeLayout rl = new RelativeLayout(this);
        rl.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

        ImageView img = new ImageView(this);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                       ViewGroup.LayoutParams.MATCH_PARENT));
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setImageDrawable(drawable);
        rl.addView(img);

        TextView caption = new TextView(this);
        RelativeLayout.LayoutParams captionLayoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT);
        captionLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        caption.setLayoutParams(captionLayoutParams);
        caption.setGravity(Gravity.START);
        caption.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        caption.setTextSize(14.0f);
        caption.setText(Html.fromHtml("Foto: Jürgen Laufer, \"Biene (Maja) - Reload\"<br><a " +
                                      "href=\"https://creativecommons.org/licenses/by/2.0/de/deed" +
                                      ".de\">Some rights reserved.</a>, Quelle: <a href=\"https://piqs.de/\">www.piqs.de</a>"));
        caption.setTextColor(Color.WHITE);
        caption.setMovementMethod(LinkMovementMethod.getInstance());
        rl.addView(caption);
        return rl;
    }
    private RelativeLayout createYesNoButtons() {
        RelativeLayout btnGroup = createRelativeLayout();
        Button btnYes = createButton("Ja", "left");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextNode("ja");
            }
        });
        Button btnNo = createButton("Nein", "right");
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextNode("nein");
            }
        });
        btnGroup.addView(btnYes);
        btnGroup.addView(btnNo);
        return btnGroup;
    }
    private RelativeLayout createRestartButton() {
        RelativeLayout btnGroup = createRelativeLayout();
        Button btnRestart = createButton("Start", "start");
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartBtnGroup.setVisibility(View.GONE);
                yesNoBtnGroup.setVisibility(View.VISIBLE);
                currentNode = beeCheckList.getRoot();
                displayText.setText(currentNode.getValue());
            }
        });
        btnGroup.addView(btnRestart);
        return btnGroup;
    }
    private void goToNextNode(String answer) {
        if (answer.toLowerCase().equals("ja")) {
            currentNode = currentNode.getLeft();
        } else if (answer.toLowerCase().equals("nein")) {
            currentNode = currentNode.getRight();
        }
        displayText.setText(currentNode.getValue());
        if (!currentNode.isInternal()) {
            // last node
            yesNoBtnGroup.setVisibility(View.GONE);
            restartBtnGroup.setVisibility(View.VISIBLE);
        }
    }

    private void createDecisionTree() {
        // create decision tree, yes is left
        BinaryNode<String> fourNo =
                linkBinaryNodes(new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q_no_q_no_q_no_q)),
                                new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q_no_q_no_q_no_q_yes_a)),
                                new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q_no_q_no_q_no_q_no_a)));
        BinaryNode<String> threeNo =
                linkBinaryNodes(new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q_no_q_no_q)),
                                new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q_no_q_no_q_yes_a)),
                                fourNo);
        BinaryNode<String> threeYes =
                linkBinaryNodes(new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q_no_q_yes_q)),
                                new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q_no_q_yes_q_yes_a)),
                                new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q_no_q_yes_q_no_a)));
        BinaryNode<String> twoNo =
                linkBinaryNodes(new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q_no_q)),
                                threeYes,
                                threeNo);
        BinaryNode<String> oneNo =
                linkBinaryNodes(new BinaryNode<>(getResources().getString(R.string.wildbienen_root_no_q)),
                                new BinaryNode<>(getResources().getString(R.string.wildbienen_root_no_q_yes_a)),
                                new BinaryNode<>(getResources().getString(R.string.wildbienen_root_no_q_no_a)));
        BinaryNode<String> oneYes =
                linkBinaryNodes(new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q)),
                                new BinaryNode<>(getResources().getString(R.string.wildbienen_root_yes_q_yes_a)),
                                twoNo);
        BinaryNode<String> root =
                linkBinaryNodes(new BinaryNode<>(getResources().getString(R.string.wildbienen_root)),
                                oneYes,
                                oneNo);

        beeCheckList = new BinaryTree<String>(root);
    }
    private BinaryNode<String> linkBinaryNodes(BinaryNode<String> rootNode, BinaryNode<String> leftNode, BinaryNode<String> rightNode) {
        rootNode.setLeft(leftNode);
        rootNode.setRight(rightNode);
        return rootNode;
    }

    /*
     * R                                Dichte Haarbüschel?
     *                               ja /          \ nein
     * 1        Dichte Haarbüschel an Beinen?      helle gesichtszeichnung, helle Flecken?
     *       ja /                 \ nein             ja /                  \ nein
     * 2    Pelzbiene    Beobachtet Frühjahr?    Maskenbienen          Solitäre Wespen
     *                    |               |
     *                ja /                 \ nein (ab Mai)
     * 3      spärlich behaart?            stark behaart?
     *      ja /        \ nein         ja /              \ nein
     * 4  Scherenbiene  Mauerbiene  Blattschneiderbiene   Körperlänge unter 10mm?
     *                                                    ja /          \ nein
     * 5                                              Löcherbiene      Wollbiene
     */
    /*
     *                   ______R______
     *                __1Y__        _1N_
     *               |   __2N___   |    |
     *                 _3Y_   _3N__
     *                |    | |   _4N_
     *                          |    |
     */
    /*
     * Dichte Haarbüschel?
     * 1Y Dichte Haarbüschel an Beinen?
     * 1N Helle Zeichnung?
     * 2N Beobachtet Frühjahr?
     * 3Y Spärlich behaart?
     * 3N Stark behaart?
     * 4N Körperlänge unter 10mm?
     */
}


