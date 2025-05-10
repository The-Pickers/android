package gsc.ZupStar.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.databinding.ActivityLoginBinding
import gsc.ZupStar.util.StatusBarUtil

@AndroidEntryPoint
class EditTeamActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        StatusBarUtil.updateStatusBarColor(this, ContextCompat.getColor(this, R.color.white))
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_login, SearchTeamFragment()
        ).commit()

    }
}