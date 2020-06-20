using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;

public class ProgramacionPuntuacion : MonoBehaviour
{
    
    public static int puntuacion = 0;

    // Start is called before the first frame update
    void Start()
    {
        puntuacion = 0;    
    }

    // Update is called once per frame
    void Update()
    {
        GetComponent<TextMeshProUGUI>().text = puntuacion.ToString();
    }
}
