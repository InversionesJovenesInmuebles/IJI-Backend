resource "aws_iam_role" "inversion_role" {
  name               = "inversion-role"
  assume_role_policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = { Service = "ec2.amazonaws.com" }
        Action = "sts:AssumeRole"
      }
    ]
  })
}

resource "aws_iam_role_policy" "instance_policy" {
  name = "instance-policy"
  role = aws_iam_role.inversion_role.id

  policy = jsonencode({
    Version   = "2012-10-17"
    Statement = [
      {
        Action = [
          "ec2:Describe*",
          "ec2:AttachVolume",
          "ec2:DetachVolume",
          "ec2:ModifyVolume"
        ]
        Effect   = "Allow"
        Resource = "*"
      }
    ]
  })
}

resource "aws_iam_instance_profile" "inversion_profile" {
  name = "inversion-profile"
  role = aws_iam_role.inversion_role.name
}
