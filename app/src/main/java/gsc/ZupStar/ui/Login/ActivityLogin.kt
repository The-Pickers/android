package gsc.ZupStar.ui.Login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.databinding.ActivityLoginBinding

@AndroidEntryPoint
class ActivityLogin:AppCompatActivity() {
    private val TAG = javaClass.simpleName
    lateinit var binding: ActivityLoginBinding
    private val fragmentList = listOf<Fragment>(FragmentSignIn(),FragmentSignUp())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_login,FragmentSignIn()
        ).commit()

    }
}