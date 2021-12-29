FROM debian:11

ENV LC_ALL C.UTF-8

# init
ADD ./release/ /root/
RUN bash /root/init_debian.sh && rm -rf /root/init_debian.sh

WORKDIR /root

EXPOSE 8899

CMD ["bash", "/root/run.sh"]
