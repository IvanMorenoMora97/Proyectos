using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ProgramacionObstaculo : MonoBehaviour
{

    public float velocity;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        transform.position += Vector3.left * velocity * Time.deltaTime;
    }
}
