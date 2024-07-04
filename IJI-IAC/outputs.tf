output "security_group" {
  value = aws_instance.microservice.vpc_security_group_ids
}

output "instance_ip" {
  value = aws_instance.microservice.public_ip
  description = "Dirección IP pública de la instancia EC2"
}

output "server_public_dns" {
  value = aws_instance.microservice.public_dns
  description = "DNS público de la instancia EC2"
}