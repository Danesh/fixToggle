package com.danesh.fixtoggle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FixToggleActivity extends Activity implements OnClickListener{
    Button reboot,fix;
    ShellCommand cmd = new ShellCommand();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        reboot = (Button)findViewById(R.id.reboot);
        fix = (Button)findViewById(R.id.fix);
        reboot.setOnClickListener(this);
        fix.setOnClickListener(this);
        if (!cmd.canSU()){
            Toast.makeText(getApplicationContext(), "No root found", Toast.LENGTH_LONG).show();
            reboot.setEnabled(false);
            fix.setEnabled(false);
        }
    }

    @Override
    public void onClick(View arg0) {
        if (arg0 == fix){
            String command = "sqlite3 /data/data/com.android.providers.settings/databases/settings.db 'update secure set value=\"1\" where name=\"preferred_network_mode\"'";
            cmd.su.run(command);
            Toast.makeText(getApplicationContext(), "Fix applied", Toast.LENGTH_LONG).show();
        }else{
            cmd.su.run("reboot");
        }
    }
}