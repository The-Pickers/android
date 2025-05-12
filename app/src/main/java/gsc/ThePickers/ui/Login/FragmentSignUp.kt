package gsc.ThePickers.ui.Login

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gsc.ThePickers.R
import gsc.ThePickers.data.SignUpData
import gsc.ThePickers.databinding.FragmentSignUpBinding
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class FragmentSignUp :Fragment() {
    private val TAG = javaClass.simpleName
    lateinit var binding: FragmentSignUpBinding
    lateinit var spf : SharedPreferences
    private val viewModel : UserViewModel by viewModels()


    private lateinit var galleryPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        spf = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        setView()
        checkInput()
        setTextEditor()
        setUpObserver()
        binding.tvChangeView.setOnClickListener {
            changeFragment()
        }
        binding.ivProfileImg.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        binding.btnEnter.setOnClickListener {
            val data = SignUpData(
                name = binding.etInputName.text.toString(),
                id =  binding.etInputEmail.text.toString(),
                password = binding.etInputPassword.text.toString()
            )
//            // 더미용
//            spf.edit().putString("name",binding.etInputName.text.toString()).apply()
//            spf.edit().putInt("token",1).apply()
//            Log.d(TAG,"save")
           viewModel.signUp(data)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            if (uri != null) {
                Log.d(TAG, "이미지 선택됨: $uri")
                val persistedUri = persistImageFromUri(uri)
                binding.ivProfileImg.setImageURI(persistedUri)
                spf.edit().putString("profile_image", persistedUri.toString()).apply()

            } else {
                Log.d(TAG, "이미지 선택 실패 또는 취소됨")
            }
        }

        galleryPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(requireContext(), "갤러리 접근 권한이 필요합니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpObserver(){
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it==null) return@Observer
            spf.edit().putString("name",binding.etInputName.text.toString()).apply()
            if (it) changeFragment()
        })
    }

    private fun setView() {
        binding.tvTitle.text = "Create Account"
        binding.tvName.visibility = View.VISIBLE
        binding.etInputName.visibility = View.VISIBLE
        binding.btnEnter.text = "Sign Up"
        binding.tvChangeView.setText(Html.fromHtml("<u>" + "Sign in" + "</u>"));
        binding.tvInfo.text = "Already have an account?"
    }

    private fun setTextEditor(){
        binding.etInputName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                checkInput()
            }
        })
        binding.etInputEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                checkInput()
            }
        })
        binding.etInputPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                checkInput()
            }
        })
    }

    private fun checkInput() {
        val nameFlag : Boolean = binding.etInputName.text.isNotEmpty()
        val emailFlag : Boolean = binding.etInputEmail.text.isNotEmpty()
        val pwFlag : Boolean = binding.etInputPassword.text.isNotEmpty()
        binding.btnEnter.isEnabled = nameFlag && emailFlag && pwFlag
    }

    private fun changeFragment(){
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.fragment_login, FragmentSignIn()
        ).commit()
    }

    private fun openGallery() {
        Log.d(TAG,"open Gallery")
        imagePickerLauncher.launch("image/*")
    }

    private fun persistImageFromUri(uri: Uri): Uri {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val filename = "profile_${System.currentTimeMillis()}.jpg"
        val file = File(requireContext().cacheDir, filename)
        inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input?.copyTo(output)
            }
        }
        return FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.fileprovider", file)
    }
}
