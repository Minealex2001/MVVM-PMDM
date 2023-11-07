package com.alejandro.mvvm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alejandro.mvvm.databinding.FragmentMiHipotecaBinding;

/**
 * MiHipotecaFragment
 * Clase que representa la vista principal de la aplicación.
 * Esta clase se encarga de mostrar la vista principal de la aplicación.
 */
public class MiHipotecaFragment extends Fragment {

    // Variable que representa el binding de la vista.
    private FragmentMiHipotecaBinding binding;

    /**
     * Constructor de la clase.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiHipotecaBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Método que se ejecuta cuando la vista se ha creado.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Instanciamos el ViewModel.
        final MiHipotecaViewModel miHipotecaViewModel = new ViewModelProvider(this).get(MiHipotecaViewModel.class);

        // Asignamos el ViewModel al binding.
        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double capital = Double.parseDouble(binding.capital.getText().toString());
                int plazo = Integer.parseInt(binding.plazo.getText().toString());

                miHipotecaViewModel.calcular(capital, plazo);
            }
        });

        // Observamos los cambios en el ViewModel.
        miHipotecaViewModel.cuota.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double cuota) {
                binding.cuota.setText(String.format("%.2f",cuota));
            }
        });

        // Observamos los cambios en el ViewModel.
        miHipotecaViewModel.errorCapital.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double capitalMinimo) {
                if (capitalMinimo != null) {
                    binding.capital.setError("El capital no puede ser inferor a " + capitalMinimo + " euros");
                } else {
                    binding.capital.setError(null);
                }
            }
        });

        miHipotecaViewModel.errorPlazos.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer plazoMinimo) {
                if (plazoMinimo != null) {
                    binding.plazo.setError("El plazo no puede ser inferior a " + plazoMinimo + " años");
                } else {
                    binding.plazo.setError(null);
                }
            }
        });

        miHipotecaViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.cuota.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.cuota.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}