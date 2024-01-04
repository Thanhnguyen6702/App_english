package samples.speech.cognitiveservices.microsoft.myapplication.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import samples.speech.cognitiveservices.microsoft.myapplication.R;

public class DialogPron extends Dialog {

    public DialogPron(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_pron);

    }
}
