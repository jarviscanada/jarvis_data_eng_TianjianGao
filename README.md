# Tianjian Lucas Gao . Jarvis Consulting

Highly motivated and analytical Computer Science graduate and finance enthusiast with hands-on experience in Java, Python, and SQL full-stack development. I hold a masters's degree in Machine Learning from the University of Montreal and dual bachelor’s degrees in Computer Science and French Studies from Penn State University. Professionally, I have worked as a C# and SQL backend developer at a leading FinTech company and completed three internships in application development and data science. In my free time, I develop chatbots, LLM applications, and am currently working on an mobile application. I’m proficient in Java and SQL and their ecosystems, with a strong foundation in data structures and algorithms, multi-threading, and system programming. With a competitive programming mindset, I am eager to tackle challenges in high-performance financial applications. On a lighter note, I’m a musician who dabbles in classical, jazz and rock piano, a dedicated yogi, and have maintained a 1500-day streak on Duolingo (perseverance is key to success).

## Skills

**Proficient:** Java/Maven/Spring, Linux/Bash, RDBMS/SQL, Agile/Scrum/Jira, Git/GitHub, Python, Sklearn/PyTorch

**Competent:** C/C++, C#/.Net, REST-API/GraphQL, HTML/CSS/JavaScript, AWS/GCP

**Familiar:** Flutter, React, Jenkins, NodeJS, Angular, PySpark/Scala

## Jarvis Projects

Project source code: [https://github.com/jarviscanada/jarvis_data_eng_tianjiangao](https://github.com/jarviscanada/jarvis_data_eng_tianjiangao)


**Linux Cluster Monitoring** [[GitHub](https://github.com/jarviscanada/jarvis_data_eng_tianjiangao/tree/master/linux_sql)]: Automated a Linux cluster monitoring agent on GCP using Bash scripts, Docker, and PostgreSQL - Linux administrators often require monitoring the resource usage (e.g. CPU, memory, and disk) of each cluster node in real time, for logging and resource planning purposes. The project leverages Docker to provision a Postgres database to support automated logging and admin querying using SQL. System information is captured using Linux commands. Crontab is used to schedule updates to the host usage table at every minute via SQL. The scripts are tested on a remote GCP virtual server running on Rocky Linux.

**SQL Tennis Court Management** [[GitHub](https://github.com/jarviscanada/jarvis_data_eng_tianjiangao/tree/master/linux_sql)]: Created a PostgreSQL database for managing sessions, members, and facilities of a tennis court. Implemented SQL queries for efficient CRUD operations and data aggregation. The dataset includes tables for members, facilities, and bookings, each with unique structures that reflect common issues found in real-world databases. The project includes exercises on key PostgreSQL topics — DDL (Data Definition Language), DML (Data Manipulation Language), joins, aggregations, string operations, and window functions.

**Java Grep App** [[GitHub](https://github.com/jarviscanada/jarvis_data_eng_tianjiangao/tree/master/core_java/grep)]: Grep is a popular Linux command-line utility for searching patterns in plaintext using regular expressions. However, it might not be easily accessible on certain operating systems such as Windows. As such, I have developed an Java application to emulate Linux Grep, using Java's Pattern library. To optimize memory usage, the Java Stream API is also used in conjunction with Lambda expressions to allow streaming for processing instead of having to load all data into the memory, achieving 25% reduction in memory usage. The application is unit tested extensively using JUnit and project dependencies are managed using Maven. The application is dockerized and can be accessed using a Windows terminal.

**Java Stock Quote App** [[GitHub](https://github.com/jarviscanada/jarvis_data_eng_tianjiangao/tree/master/core_java/jdbc)]: (In development) Developed a Java application to obtain stock quotes using the Alpha Vantage API. The stock quotes are stored in a Postgres database via JDBC using the Data Access Object (DAO) pattern. The API and DAO are tested using the JUnit and Mockito frameworks to ensure consistent data access and storage. Developed REST API.


## Highlighted Projects
**LLM PDF and YouTube summarizers**: Leveraged LangChain and open-sourced large language models (LLM) such as Llama-3 and Gemma-2 via llama.cpp and Python APIs to summarize PDF documents and YouTube video transcripts. Utilized Chroma and Weaviate vector databases for efficient text storage and indexing. Compared Faiss, ScaNN, and Annoy similarity-search algorithms. Applied prompt engineering techniques such as in-context learning, few-shot learning and chain-of-thoughts for better reasoning performance.

**French Restaurant Reservation Agent (Chatbot)**: Created a chatbot for initiating and managing restaurant reservations using Rasa. Defined user intents, actions, slots, and responses. Designed conversation flows with ManyChat, and deployed the application using Docker and ngrok. Optimization of the chatbot with Rasa X (CI/CD) and integration with Facebook Messenger. Currently reimplementing the bot on Google Dialogflow CX.

**Deep Learning from Scratch** [[GitHub](https://github.com/CestLucas/Convolutional-Neural-Network)]: Implemented deep learning models including CNN, RNN, GRU, LSTM, VAE, GAN, and GPT-2, along with core components including attention mechanisms, tokenizers, KL divergence, and activation functions, using Python 3 and NumPy.

**RPG Game Strategy Optimization** [[GitHub](https://github.com/CestLucas/Approximate-dynamic-programming/)]: Leveraged Monte Carlo method and dynamic programming algorithms such as approximate value iteration to explore optimal level-up strategies for a text-based RPG game written in Python 3, optimized using multi-threading.

**JavaScript and Frontend Development**: Recreated the dynamic scrolling mechanism of apple.com with JavaScript Scroll Magic and worked on the Odin and freeCodeCamp frontend projects.

**Brownian Motion Simulation for Quantitative Finance**: Explored the stochastic process and quadratic variation to approximate Brownian motion. Loaded stock data from Yahoo Finance. Implemented Monte Carlo simulations for financial modeling using SciPy and Pandas.


## Professional Experiences

**Software Developer, Jarvis (2024-present)**: Developed a Linux cluster monitoring agent on GCP by creating Bash scripts to pull system usage info into a dockerized PostgreSQL instance, saving DevOps time. Automated the process by using Crontab. Created a PostgreSQL database for tennis course management and implemented SQL queries for efficient CRUD operations and data aggregation. Created dockerized Java web apps for searching text patterns and for obtaining stock quotes using Stream, Lambda, and Alpha Vantage APIs. Managed microservices using Spring Boot, database transactions using JDBC, and build automation using Maven. Tested the apps using Junit and Mockito.

**Lead NLP Developer, Mobile App for Speech Disorders, Vocality (2024-present)**: Developing on-device solutions for context-based sentence prediction and autocomplete serving individuals struggling with speech disorders, using tflite-flutter, on-device LLM and Markov chains. Building a RAG agent with langchain-dart for storing conversation history.

**Backend Developer, Wealth Management Platform, FNZ Group (2023-2024)**: Optimized transaction backend using C# and Microsoft SQL Server for a $1.5T AUM wealth management platform. Developed REST APIs using parameter binding in .NET and tested server response using Insomnia (Postman). Gained valuable knowledge and training in finance and financial markets.

**Backend Developer Intern, Senior-Care Chatbot, Innovention (2023-2023)**: Developed a natural language understanding (NLU) module using StringBuilder, LINQ, and Stanford NLP to extract entities and actions from user input for a custom chatbot platform. Built a knowledge graph using Neo4j, improving “common sense” question answering by 100%. Implemented a CRUD interface for a SQLite database in C# to store user info, and wrote unit tests for optimization using xUnit. Experimented with retrieval-augmented generation (RAG) using Chroma and LangChain for indexing user info and knowledge.

**Data Scientist Intern, Data-driven Analytics, International Air and Transportation Association (2021-2022)**: Leveraged keyword modeling (AutoPhrase), topic modeling (LDA), unsupervised clustering, and taxonomy generation to extract over 100 new aviation incident topics from 2 million aviation incident reports, helping improve aviation safety. Implemented algorithms from research, ported code from Python 2 to Python 3 and edited shell scripts for model training. Worked within a cross-functional team to share classification models, word embeddings, and taxonomies with member airlines, reducing time and cost for data analytics.

**Data Scientist Intern, Data-driven Analytics, International Air and Transportation Association (2020-2021)**: Used machine learning models for text analysis and integrated them into the IATA dashboard. Preprocessed 2 million multi-lingual aviation reports using spaCy and Gensim. Detected text languages using TextBlob. Trained machine learning models using Scikit-learn, as well as BERT and T5 language models to classify reports into a multi-level taxonomy using PyTorch, facilitating manual classification. Analyzed the aviation reports using Pandas, spaCy, Matplotlib, and TensorBoard. Communicated methodologies and complex data findings effectively with stakeholders.


## Education
**Université de Montréal (2019-2022)**, M.Sc. Computer Science, MILA/RALI-DIRO
- Full Tuition Exemption Scholarship ($24 000)
- Scholarship of Excellence ($5 700)
- Excellent Thesis Award
- GPA: 3.83/4.3

**Penn State University (2015-2019)**, B.Sc. Computer Science & French Studies (double major), Electrical Engineering and Computer Engineering & School of Liberal Arts
- Semester Abroad Grant at University of Montpellier ($ 1 500)
- Dean’s List 2016, 2017
- Advanced Vehicle Club (ADAS) | Robotic Club (Arduino) | French Club


## Miscellaneous
- AWS Certified Cloud Practitioner
- AWS Certified Machine Learning – Specialty
- Deeplearning.AI Generative AI with Large Language Models
- IVADO Chatbot Creation with Rasa and Docker
- IVADO Knowledge Graph Development with Neo4j
- FreeCodeCamp Responsive Web Design (HTML, CSS, Flexbox, CSS Grid)
- 7-year yoga practitioner. I follow Tim Senesi on YouTube 🧘‍♂️
- 5-year hiker. My favorite trails are Mont Orford and Mont Sutton 🥾
- Avid reader. Currently reading Les Miserables, Neuromancer, and The Tommyknockers 📖
- 15-year pianist. Music festival frequenter. Audiophile (HD600 and K612 Pro) 🎶
- LeetCoder for competitive programming
- Volunteer, Penn State Thon for children cancer prevention
- Language learner proficient in English, French, Mandarin Chinese, and Japanese. I have a 1500 day streak on Duolingo 🦉
- Globaltrotter. China, United States, France, Spain, Germany, UK, Japan (the list goes on) ✈️
- I am also ambidexterous!