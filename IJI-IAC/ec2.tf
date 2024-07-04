resource "aws_security_group" "instance" {
  name        = "instance-security-group"
  description = "Allow inbound traffic on ports 8001 and 8002"

  ingress = [
    {
      description      = "Allow SSH access from anywhere"
      from_port        = 22
      to_port          = 22
      protocol         = "tcp"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = []
      prefix_list_ids  = []
      security_groups  = []
      self             = true
    },
    {
      description      = "Allow HTTP access from anywhere"
      from_port        = 80
      to_port          = 80
      protocol         = "tcp"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = []
      prefix_list_ids  = []
      security_groups  = []
      self             = true
    },
    {
      description      = "Allow HTTP access from anywhere"
      from_port        = 8080
      to_port          = 8080
      protocol         = "tcp"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = []
      prefix_list_ids  = []
      security_groups  = []
      self             = true
    },
    {
      description      = "Allow HTTP access from anywhere"
      from_port        = 8083
      to_port          = 8083
      protocol         = "tcp"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = []
      prefix_list_ids  = []
      security_groups  = []
      self             = true
    },
    {
      description      = "Allow HTTP access from anywhere"
      from_port        = 5433
      to_port          = 5433
      protocol         = "tcp"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = []
      prefix_list_ids  = []
      security_groups  = []
      self             = true
    }
  ]

  egress = [
    {
      description      = "Allow all outbound traffic"
      from_port        = 0
      to_port          = 0
      protocol         = "-1"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = ["::/0"]
      prefix_list_ids  = []
      security_groups  = []
      self             = true
    }
  ]

  tags = {
    Name = "inversiones-sg"
    Environment = "develop"
    Owner = "frankecb07@gmail.com"
    Team = "Backend"
    Project = "InversionesJI"
  }
}

resource "aws_key_pair" "deployer" {
  key_name = "IJI-server-ssh"
  public_key = file("IJI.key.pub")

  tags = {
    Name = "inversiones-ssh"
    Environment = "develop"
    Owner = "frankecb07@gmail.com"
    Team = "Backend"
    Project = "InversionesJI"
  }
}

resource "aws_instance" "microservice" {
  ami                  = "ami-06c68f701d8090592"
  instance_type        = "t2.micro"
  key_name             = aws_key_pair.deployer.key_name
  iam_instance_profile = aws_iam_instance_profile.instance_profile.name
  vpc_security_group_ids = [aws_security_group.instance.id]

  tags = {
    Name = "inversiones-microservice"
    Environment = "develop"
    Owner = "frankecb07@gmail.com"
    Team = "Backend"
    Project = "InversionesJI"
  }

  user_data = <<-EOF
              #!/bin/bash
              sudo yum update -y
              sudo yum install -y docker git
              sudo systemctl start docker
              sudo systemctl enable docker
              sudo usermod -aG docker ec2-user
              sudo curl -L "https://github.com/docker/compose/releases/download/v2.19.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
              sudo chmod +x /usr/local/bin/docker-compose

              # Cloning the repository and starting Docker Compose
              git clone https://github.com/Frankcb04/docker-compose-Microservice.git /home/ec2-user/docker-compose-Microservice
              cd /home/ec2-user/docker-compose-Microservice
              sudo docker-compose up -d
              EOF

  depends_on = [aws_iam_instance_profile.instance_profile]
}