FROM postgres:16

RUN apt-get update \
    && apt-get install -y postgis postgresql-16-postgis-3 postgresql-16-postgis-3-scripts \
    && rm -rf /var/lib/apt/lists/*