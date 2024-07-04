variable "tags"{
  type = map(map(string))
  description = "Tags for AWS resources"
}

variable "aws_role" {
  description = "AWS Role"
  type = map(string)
}