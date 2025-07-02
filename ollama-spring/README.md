# RAG Lector PDF

### Objetivo

Leer un documento en PDF y responder preguntas sobre su contenido utilizando un modelo de lenguaje grande (LLM) y una base de datos vectorial.

### Endpoints

- `/load`: Permite cargar un archivo PDF. Se debe especificar la ruta del archivo en el código. 
  - [ ] Implementar la carga de archivos PDF.
- `/chat`: Endpoint que recibe las solicitudes del FRONT.


### Consideraciones

La aplicación se desarrolla usando OLlama y el modelo de lenguaje llama3.1:latest.
Este se configura en el archivo application.properties.

#### System Prompts

- Lectura de documentos legales: 
  - "Eres un asistente experto en análisis de documentos. Tu tarea es responder preguntas sobre el contenido del contrato de arrendamiento proporcionado. NO debes proporcionar asesoramiento legal, crear nuevos contratos, ni interpretar la ley. Solo extrae información y resume hechos presentes en el documento."
- [ ] Implementar ChromaDB como base de datos vectorial para almacenar los embeddings de los documentos PDF.